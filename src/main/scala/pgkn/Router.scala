package pgkn

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import upickle.default.{ReadWriter, macroRW}
import pgkn.pages.{Home, Sigrid}

sealed abstract class Page(val title: String) derives ReadWriter
case object HomePage extends Page("Home")
case object SigridPage extends Page("Sigrid")

object Router:
  val homeRoute = Route.static(HomePage, root / endOfSegments)
  val sigridRoute = Route.static(SigridPage, root / "sigrid" / endOfSegments)

  private object RouterInstance
      extends Router[Page](
        routes = List(homeRoute, sigridRoute),
        getPageTitle = _.title,
        serializePage = page => upickle.default.write(page),
        deserializePage = pageStr => upickle.default.read[Page](pageStr)
      )

  val splitter =
    SplitRender[Page, HtmlElement](RouterInstance.currentPageSignal)
      .collectStatic(HomePage)(Home.element(RouterInstance))
      .collectStatic(SigridPage)(Sigrid.element(RouterInstance))
