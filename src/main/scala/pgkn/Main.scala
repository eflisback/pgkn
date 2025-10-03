package pgkn

import org.scalajs.dom
import com.raquo.laminar.api.L.*

@main
def run: Unit =
  renderOnDomContentLoaded(
    dom.document.getElementById("app"),
    div(
      child <-- Router.splitter.signal
    )
  )
