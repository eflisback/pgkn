package pgkn

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import upickle.default.{ReadWriter, macroRW}
import pgkn.pages.{Home, Sigrid, NotFound}
import pgkn.pages.KaptenAlloc

sealed abstract class Page(val title: String) derives ReadWriter
case object HomePage extends Page("PEGEKÅN")
case object KaptenAllocPage extends Page("PGKN - Kapten Alloc")
case object SigridPage extends Page("PGKN - Sigrid")
case class NotFoundPage(path: String) extends Page("PGKN - 404")

object Router:
  val homeRoute = Route.static(HomePage, root / endOfSegments)
  val kaptenAllocRoute =
    Route.static(KaptenAllocPage, root / "kapten-alloc" / endOfSegments)
  val sigridRoute = Route.static(SigridPage, root / "sigrid" / endOfSegments)

  private object RouterInstance
      extends Router[Page](
        routes = List(homeRoute, kaptenAllocRoute, sigridRoute),
        getPageTitle = _.title,
        serializePage = page => upickle.default.write(page),
        deserializePage = pageStr => upickle.default.read[Page](pageStr),
        routeFallback = path => NotFoundPage(path)
      )

  val splitter =
    SplitRender[Page, HtmlElement](RouterInstance.currentPageSignal)
      .collectStatic(HomePage)(Home(RouterInstance))
      .collectStatic(KaptenAllocPage)(KaptenAlloc(RouterInstance))
      .collectStatic(SigridPage)(Sigrid(RouterInstance))
      .collect[NotFoundPage] { case page =>
        NotFound(RouterInstance, page.path)
      }
