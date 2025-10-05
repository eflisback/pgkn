package pgkn

import org.scalajs.dom
import com.raquo.laminar.api.L.*
import pgkn.services.ThemeService

@main
def run: Unit =
  ThemeService.initialize()

  renderOnDomContentLoaded(
    dom.document.getElementById("app"),
    div(
      child <-- Router.splitter.signal
    )
  )
