package pgkn.components

import com.raquo.waypoint.Router
import com.raquo.laminar.api.L.*

object NavHeader:
  def element(router: Router[pgkn.Page]): Element =
    headerTag(
      navTag(
        h1("PGKN App"),
        a(router.navigateTo(pgkn.HomePage), "Home"),
        a(router.navigateTo(pgkn.SigridPage), "Sigrid")
      )
    )
