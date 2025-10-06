package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import pgkn.components.NavHeader
import pgkn.components.SvgIcon
import pgkn.services.KaptenAllocDataService
import scala.scalajs.js

object KaptenAlloc:
  KaptenAllocDataService.fetchData()

  def apply(router: Router[pgkn.Page]): HtmlElement =
    val searchQuery = Var("")
    val showPassed = Var(false)

    val timeFilteredEntries =
      KaptenAllocDataService.entries
        .combineWith(showPassed.signal)
        .map((entries, showPassedSessions) =>
          if showPassedSessions then entries
          else
            val now = new js.Date().getTime()
            entries.filter(_.time.toDouble >= now)
        )

    val formattedEntries = timeFilteredEntries.map(_.map(_.toFormatted))

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
          className := "kapten-alloc-controls",
          label(
            input(
              typ := "checkbox",
              checked <-- showPassed.signal,
              onInput.mapToChecked --> showPassed
            ),
            span("Inkludera passerade tider")
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
      )
    )
