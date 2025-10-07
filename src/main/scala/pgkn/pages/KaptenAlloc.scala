package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.laminar.lifecycle.MountContext
import com.raquo.waypoint.*
import pgkn.components.NavHeader
import pgkn.components.SvgIcon
import pgkn.services.KaptenAllocDataService
import pgkn.services.DownloadService
import pgkn.utils.ics.*
import org.scalajs.dom
import scala.scalajs.js
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object KaptenAlloc:
  KaptenAllocDataService.fetchData()

  private def buildCalendar(
      entries: Seq[pgkn.model.KaptenAllocEntry]
  ): Calendar =
    val calendar = Calendar()

    entries.foreach(entry =>
      val instant = Instant.ofEpochMilli(entry.time.toLong)
      val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

      val date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
      val time = localDateTime.format(DateTimeFormatter.ofPattern("HHmm")).toInt

      val event = Event()
      event.addProperty(
        (Property.time(date, time) ++
          Seq(
            Property.uid(),
            Property.summary("EDAA50", entry.entryType, entry.room),
            Property.description("EDAA50", entry.group, entry.room),
            Property.location(entry.room),
            Property.tzid()
          ) ++
          Property.createdTimes())*
      )
      calendar.addEvent(event)
    )

    calendar

  private def scrollToEntry(
      ctx: MountContext[?],
      entryId: String,
      selectedId: Option[String]
  ): Unit =
    selectedId.foreach(id =>
      if id == entryId then
        js.timers.setTimeout(100)({
          val options = js.Dynamic
            .literal(behavior = "smooth", block = "center", inline = "nearest")
            .asInstanceOf[js.Any]
          ctx.thisNode.ref.asInstanceOf[js.Dynamic].scrollIntoView(options)
        })
    )

  private def copyEntryLink(entryId: String): Unit =
    val url = s"${dom.window.location.origin}/kapten-alloc?selected=$entryId"
    dom.window.navigator.clipboard.writeText(url)

  private def renderTableRow(
      entry: pgkn.model.FormattedKaptenAllocEntry,
      selectedId: Option[String]
  ): HtmlElement =
    tr(
      dataAttr("entry-id") := entry.id,
      className := selectedId
        .filter(_ == entry.id)
        .map(_ => "selected")
        .getOrElse(""),
      onMountCallback(ctx => scrollToEntry(ctx, entry.id, selectedId)),
      onClick --> (_ => copyEntryLink(entry.id)),
      td(entry.entryType),
      td(entry.dateStr),
      td(entry.weekNum),
      td(entry.dayStr),
      td(entry.timeStr),
      td(entry.group),
      td(entry.room),
      td(entry.supervisor)
    )

  def apply(
      router: Router[pgkn.Page],
      selectedId: Option[String]
  ): HtmlElement =
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

    val searchFilteredEntries =
      timeFilteredEntries
        .combineWith(searchQuery.signal)
        .map((entries, query) =>
          if query.trim.isEmpty then entries
          else
            val lowerQuery = query.toLowerCase
            entries.filter(entry =>
              entry.entryType.toLowerCase.contains(lowerQuery) ||
                entry.group.toLowerCase.contains(lowerQuery) ||
                entry.room.toLowerCase.contains(lowerQuery) ||
                entry.supervisor.toLowerCase.contains(lowerQuery)
            )
        )

    val formattedEntries = searchFilteredEntries.map(_.map(_.toFormatted))

    val filteredEntries = formattedEntries

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
              SvgIcon("/icons/download.svg"),
              onClick
                .compose(_.withCurrentValueOf(searchFilteredEntries))
                --> ((_, entries) =>
                  if entries.isEmpty then
                    dom.window.alert("Finns inga tider att skapa en ICS fil av")
                  else
                    val calendar = buildCalendar(entries)
                    DownloadService.downloadIcs(calendar.toICS())
                )
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
                _.map(entry => renderTableRow(entry, selectedId))
              )
            )
          )
        )
      )
    )
