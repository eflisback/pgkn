package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import pgkn.components.NavHeader
import pgkn.components.SvgIcon
import pgkn.services.KaptenAllocDataService
import pgkn.model.KaptenAllocEntry
import pgkn.utils.DateUtils
import pgkn.utils.FormatUtils.*
import scala.scalajs.js

object KaptenAlloc:
  KaptenAllocDataService.fetchData()

  private case class FormattedEntry(
      entryType: String,
      dateStr: String,
      weekNum: String,
      dayStr: String,
      timeStr: String,
      group: String,
      room: String,
      supervisor: String
  )

  private def formatEntry(entry: KaptenAllocEntry): FormattedEntry =
    val date = new js.Date(entry.time.toDouble)
    val dateStr =
      s"${(date.getMonth() + 1).toInt.pad()}-${date.getDate().toInt.pad()}"
    val timeStr =
      s"${date.getHours().toInt.pad()}:${date.getMinutes().toInt.pad()}"
    val weekNum = s"v${DateUtils.getWeekNumber(entry.time)}"
    val dayStr = DateUtils.getSwedishDayName(entry.time)

    FormattedEntry(
      entry.entryType,
      dateStr,
      weekNum,
      dayStr,
      timeStr,
      entry.group,
      entry.room,
      entry.supervisor
    )

  def apply(router: Router[pgkn.Page]): HtmlElement =
    val searchQuery = Var("")

    val formattedEntries =
      KaptenAllocDataService.entries.map(_.map(formatEntry))

    val filteredEntries =
      formattedEntries
        .combineWith(searchQuery.signal)
        .map((entries, query) =>
          if query.trim.isEmpty then entries
          else
            val lowerQuery = query.toLowerCase
            entries.filter(entry =>
              entry.entryType.toLowerCase.contains(lowerQuery) ||
                entry.dateStr.contains(lowerQuery) ||
                entry.weekNum.toLowerCase.contains(lowerQuery) ||
                entry.dayStr.toLowerCase.contains(lowerQuery) ||
                entry.timeStr.contains(lowerQuery) ||
                entry.group.toLowerCase.contains(lowerQuery) ||
                entry.room.toLowerCase.contains(lowerQuery) ||
                entry.supervisor.toLowerCase.contains(lowerQuery)
            )
        )

    mainTag(
      className := "kapten-alloc-page",
      NavHeader(router),
      div(
        className := "kapten-alloc-page-content",
        div(
          className := "kapten-alloc-subheader",
          sectionTag(
            input(
              typ := "text",
              placeholder := "Sök...",
              onInput.mapToValue --> searchQuery
            )
          ),
          sectionTag(
            a(
              href := "https://cloud.timeedit.net/lu/web/lth1/ri19566250000YQQ28Z0507007y9Y4763gQ0g5X6Y65ZQ176.html",
              target := "_blank",
              span("TimeEdit"),
              SvgIcon("/icons/externalLink.svg")
            ),
            a(
              href := "https://fileadmin.cs.lth.se/cs/Bilder/Salar/Datorsalar_E-huset.pdf",
              target := "_blank",
              span("Karta"),
              SvgIcon("/icons/externalLink.svg")
            )
          ),
          sectionTag(
            button(
              span("Ladda ner"),
              SvgIcon("/icons/download.svg")
            )
          )
        ),
        div(
          className := "kapten-alloc-table-container",
          table(
            className := "kapten-alloc-table",
            thead(
              tr(
                th("Typ"),
                th("Datum"),
                th("Vecka"),
                th("Dag"),
                th("Tid"),
                th("Grupp"),
                th("Rum"),
                th("Vem?")
              )
            ),
            tbody(
              children <-- filteredEntries.map(
                _.map(entry =>
                  tr(
                    td(entry.entryType),
                    td(entry.dateStr),
                    td(entry.weekNum),
                    td(entry.dayStr),
                    td(entry.timeStr),
                    td(entry.group),
                    td(entry.room),
                    td(entry.supervisor)
                  )
                )
              )
            )
          )
        )
      ),
      div(
        className := "kapten-alloc-controls",
        p("Controls will go here")
      )
    )
