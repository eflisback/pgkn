package pgkn

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import upickle.default.{ReadWriter, macroRW}
import pgkn.pages.{Home, Sigrid}
import pgkn.pages.KaptenAlloc

sealed abstract class Page(val title: String) derives ReadWriter
case object HomePage extends Page("Home")
case object KaptenAllocPage extends Page("Kapten Alloc")
case object SigridPage extends Page("Sigrid")

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
        deserializePage = pageStr => upickle.default.read[Page](pageStr)
      )

  val splitter =
    SplitRender[Page, HtmlElement](RouterInstance.currentPageSignal)
      .collectStatic(HomePage)(Home.element(RouterInstance))
      .collectStatic(KaptenAllocPage)(KaptenAlloc.element(RouterInstance))
      .collectStatic(SigridPage)(Sigrid.element(RouterInstance))
