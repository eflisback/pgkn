package pgkn

import org.scalajs.dom
import com.raquo.laminar.api.L.{renderOnDomContentLoaded}

@main
def run: Unit =
  renderOnDomContentLoaded(
    dom.document.getElementById("app"),
    App.element()
  )
