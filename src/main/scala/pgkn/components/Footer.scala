package pgkn.components

import com.raquo.laminar.api.L.*
import pgkn.BuildInfo

object Footer:
  def apply(): HtmlElement =
    footerTag(
      className := "footer",
      a(
        href := "https://github.com/eflisback/pgkn",
        target := "_blank",
        SvgIcon("/icons/git.svg"),
        span(s"v${BuildInfo.version}")
      )
    )
