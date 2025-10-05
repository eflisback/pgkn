package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import pgkn.components.NavHeader

object Sigrid:
  def apply(router: Router[pgkn.Page]): HtmlElement =
    mainTag(
      NavHeader(router),
      div(
        className := "page-content",
        p("This is sigrid")
      )
    )
