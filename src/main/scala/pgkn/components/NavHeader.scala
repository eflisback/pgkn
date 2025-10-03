package pgkn.components

import com.raquo.waypoint.Router
import com.raquo.laminar.api.L.*

object NavHeader:
  def element(router: Router[pgkn.Page]): Element =
    headerTag(
      className := "nav-header",
      sectionTag(
        a(
          router.navigateTo(pgkn.HomePage),
          h1("PGK'N")
        )
      ),
      navTag(
        className := "nav-header-links",
        a(router.navigateTo(pgkn.SigridPage), "Sigrid")
      ),
      sectionTag(
        className := "nav-header-github",
        a("GitHub")
      )
    )
