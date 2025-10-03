package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import pgkn.components.NavHeader

object Home:
  def element(router: Router[pgkn.Page]): HtmlElement =
    mainTag(
      NavHeader.element(router),
      div("This is home page")
    )
