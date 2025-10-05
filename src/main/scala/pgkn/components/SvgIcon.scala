package pgkn.components

import com.raquo.laminar.api.L.*

object SvgIcon:
  def apply(src: String): HtmlElement =
    span(
      cls := "svg-icon",
      styleAttr := s"mask-image: url($src); -webkit-mask-image: url($src);"
    )
