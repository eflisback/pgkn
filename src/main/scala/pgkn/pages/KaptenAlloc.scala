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

  def apply(router: Router[pgkn.Page]): HtmlElement =
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
              placeholder := "Sök..."
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
              children <-- KaptenAllocDataService.entries.map(_.map(entry =>
                val date = new js.Date(entry.time.toDouble)
                val dateStr =
                  s"${(date.getMonth() + 1).toInt.pad()}-${date.getDate().toInt.pad()}"
                val timeStr =
                  s"${date.getHours().toInt.pad()}:${date.getMinutes().toInt.pad()}"
                val weekNum = s"v${DateUtils.getWeekNumber(entry.time)}"
                val dayStr = DateUtils.getSwedishDayName(entry.time)

                tr(
                  td(entry.entryType),
                  td(dateStr),
                  td(weekNum),
                  td(dayStr),
                  td(timeStr),
                  td(entry.group),
                  td(entry.room),
                  td(entry.supervisor)
                )
              ))
            )
          )
        )
      ),
      div(
        className := "kapten-alloc-controls",
        p("Controls will go here")
      )
    )
