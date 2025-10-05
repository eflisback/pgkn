package pgkn.components

import com.raquo.waypoint.Router
import com.raquo.laminar.api.L.*

object NavHeader:
  def apply(router: Router[pgkn.Page]): HtmlElement =
    headerTag(
      className := "nav-header",
      sectionTag(
        a(
          router.navigateTo(pgkn.HomePage),
          h2("PGK'N")
        )
      ),
      sectionTag(
        navTag(
          className := "nav-header-links",
          a(router.navigateTo(pgkn.KaptenAllocPage), "Kapten Alloc"),
          a(router.navigateTo(pgkn.SigridPage), "Sigrid"),
          a(router.navigateTo(pgkn.SigridPage), "Beppe")
        ),
        className := "nav-header-theme-switch",
        button(
          SvgIcon("/icons/light.svg")
        ) // Put icon here
      )
    )
