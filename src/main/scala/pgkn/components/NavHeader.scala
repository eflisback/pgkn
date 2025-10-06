package pgkn.components

import com.raquo.waypoint.Router
import com.raquo.laminar.api.L.*
import pgkn.services.{ThemeService, Theme}

object NavHeader:
  private val navLinks = List(
    (pgkn.KaptenAllocPage, "Kapten Alloc"),
    (pgkn.SigridPage, "Sigrid")
  )

  def apply(router: Router[pgkn.Page]): HtmlElement =
    headerTag(
      className := "nav-header",
      sectionTag(
        a(
          router.navigateTo(pgkn.HomePage),
          h2("PEGEKÅN")
        )
      ),
      sectionTag(
        navTag(
          className := "nav-header-links",
          navLinks.map((page, label) =>
            a(
              router.navigateTo(page),
              className.toggle(
                "nav-header-link-selected"
              ) <-- router.currentPageSignal.map(
                _ == page
              ),
              label
            )
          )
        ),
        className := "nav-header-theme-switch",
        button(
          onClick --> (_ => ThemeService.toggle()),
          child <-- ThemeService.theme.map(theme =>
            val iconPath = theme match
              case Theme.Light => "/icons/dark.svg"
              case Theme.Dark  => "/icons/light.svg"
            SvgIcon(iconPath)
          )
        )
      )
    )
