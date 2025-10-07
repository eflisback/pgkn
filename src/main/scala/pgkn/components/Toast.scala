package pgkn.components

import com.raquo.laminar.api.L.*

object Toast:
  def apply(message: String, isVisible: Signal[Boolean]): HtmlElement =
    div(
      className := "toast",
      className <-- isVisible.map(visible => if visible then "toast-visible" else ""),
      span(message)
    )
