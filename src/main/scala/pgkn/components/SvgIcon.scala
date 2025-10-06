package pgkn.components

import com.raquo.laminar.api.L.*

object SvgIcon:
  def apply(path: String, preserveColors: Boolean = false): HtmlElement =
    if preserveColors then
      img(
        src := path
      )
    else
      span(
        className := "svg-icon",
        styleAttr := s"mask-image: url($path); -webkit-mask-image: url($path);"
      )
