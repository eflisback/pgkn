package pgkn.model

import pgkn.utils.DateUtils
import pgkn.utils.FormatUtils.*
import pgkn.utils.HashUtils
import scala.scalajs.js

case class KaptenAllocEntry(
    entryType: String,
    time: BigInt,
    group: String,
    room: String,
    supervisor: String
):
  /** Generates a unique, stable ID for this entry based on its properties */
  lazy val id: String =
    HashUtils.shortHash(s"$time-$group-$room-$supervisor-$entryType")

  def toFormatted: FormattedKaptenAllocEntry =
    val date = new js.Date(time.toDouble)
    val dateStr =
      s"${(date.getMonth() + 1).toInt.pad()}-${date.getDate().toInt.pad()}"
    val timeStr =
      s"${date.getHours().toInt.pad()}:${date.getMinutes().toInt.pad()}"
    val weekNum = s"v${DateUtils.getWeekNumber(time)}"
    val dayStr = DateUtils.getSwedishDayName(time)

    FormattedKaptenAllocEntry(
      entryType,
      dateStr,
      weekNum,
      dayStr,
      timeStr,
      group,
      room,
      supervisor,
      id
    )

case class FormattedKaptenAllocEntry(
    entryType: String,
    dateStr: String,
    weekNum: String,
    dayStr: String,
    timeStr: String,
    group: String,
    room: String,
    supervisor: String,
    id: String
)
