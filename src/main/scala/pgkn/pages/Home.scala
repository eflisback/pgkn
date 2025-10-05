package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import pgkn.components.NavHeader

object Home:
  def apply(router: Router[pgkn.Page]): HtmlElement =
    mainTag(
      className := "home-page",
      NavHeader(router),
      div(
        className := "home-page-content",
        h1("Koda, koda, koda!"),
        span("- Björn Regnell")
      )
    )
