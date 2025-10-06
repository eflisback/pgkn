package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import pgkn.components.{NavHeader, Footer}
import scala.util.Random

object Home:
  private val quotes = List(
    ("Sekvens, Alternativ, Repetition, Abstraktion.", "Björn Regnell"),
    ("Koda, koda, koda!", "Björn Regnell"),
    ("WUUUUAAAAH!", "Mutanten"),
    ("Det är jag som är döden.", "Skräpsamlaren"),
    ("🎵 🎶 🎶", "Stränginterpolatorn"),
    ("PANG!", "Kompilatorn"),
    ("TACK kompilatorn!", "Björn Regnell"),
    ("KUT FSSR", "Björn Regnell"),
    ("val gurka: Int = 42", "Björns REPL"),
    ("Jag är anonyyyym... jag har inget naaamn...", "() => {}")
  )

  def apply(router: Router[pgkn.Page]): HtmlElement =
    val quote = Random.shuffle(quotes).head

    mainTag(
      className := "home-page",
      NavHeader(router),
      div(
        className := "home-page-content",
        h1(s"\"${quote._1}\""),
        span(s"- ${quote._2}")
      ),
      Footer()
    )
