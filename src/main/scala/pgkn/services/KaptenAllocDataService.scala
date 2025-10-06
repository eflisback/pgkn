package pgkn.services

import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.scalajs.js
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import pgkn.model.KaptenAllocEntry

object KaptenAllocDataService:
  private val dataUrl =
    "https://raw.githubusercontent.com/bjornregnell/kapten-alloc-web/refs/heads/master/src/main/scala/data-GENERATED.scala"

  private val rawDataVar = Var[Option[String]](None)
  private val entriesVar = Var[Seq[KaptenAllocEntry]](Seq.empty)
  private val loadingVar = Var(false)
  private val errorVar = Var[Option[String]](None)

  val rawData: Signal[Option[String]] = rawDataVar.signal
  val entries: Signal[Seq[KaptenAllocEntry]] = entriesVar.signal
  val loading: Signal[Boolean] = loadingVar.signal
  val error: Signal[Option[String]] = errorVar.signal

  def fetchData(): Unit =
    loadingVar.set(true)
    errorVar.set(None)

    dom
      .fetch(dataUrl)
      .toFuture
      .flatMap(response =>
        if response.ok then response.text().toFuture
        else throw new Exception(s"HTTP error: ${response.status}")
      )
      .onComplete {
        case Success(text) =>
          val extracted = extractTripleQuoteContent(text)
          rawDataVar.set(Some(extracted))
          val parsed = parseEntries(extracted)
          entriesVar.set(parsed)
          loadingVar.set(false)

        case Failure(error) =>
          errorVar.set(Some(s"Failed to fetch data: ${error.getMessage}"))
          loadingVar.set(false)
      }

  private def extractTripleQuoteContent(text: String): String =
    val tripleQuotePattern = "\"\"\"([\\s\\S]*?)\"\"\"".r
    tripleQuotePattern.findFirstMatchIn(text) match
      case Some(m) => m.group(1).trim
      case None    => ""

  private def parseEntries(rawData: String): Seq[KaptenAllocEntry] =
    val lines = rawData.split('\n').map(_.trim).filter(_.nonEmpty)

    val dataLines = lines.filterNot(line =>
      line.startsWith("---") || line.startsWith("del |")
    )

    dataLines.flatMap(parseLine).toSeq

  private def parseLine(line: String): Option[KaptenAllocEntry] =
    val parts = line.split('|').map(_.trim)

    if parts.length >= 7 then
      try
        val entryType = parts(4) // typ
        val date = parts(1) // datum
        val time = parts(3) // kl
        val group = parts(5) // grupp
        val room = parts(6) // rum
        val supervisor = if parts.length > 7 then parts(7) else ""

        val timestamp = dateTimeToTimestamp(date, time)

        Some(KaptenAllocEntry(entryType, timestamp, group, room, supervisor))
      catch case _: Exception => None
    else None

  private def dateTimeToTimestamp(date: String, time: String): BigInt =
    try
      val Array(year, month, day) = date.split('-').map(_.toInt)
      val Array(hour, minute) = time.split(':').map(_.toInt)

      val jsDate = new js.Date(year, month - 1, day, hour, minute)
      BigInt(jsDate.getTime().toLong)
    catch case _: Exception => BigInt(0)
