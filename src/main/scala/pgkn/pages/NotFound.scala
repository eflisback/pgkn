package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import pgkn.components.{NavHeader, Footer}
import scala.util.Random

object NotFound:
  private val gifs = List(
    "/gifs/john_travolta.gif",
    "/gifs/at_walker.gif",
    "/gifs/toilet.gif",
    "/gifs/borat.gif",
    "/gifs/meteorite.gif"
  )

  def apply(router: Router[pgkn.Page], path: String): HtmlElement =
    val randomGif = Random.shuffle(gifs).head

    mainTag(
      className := "not-found-page",
      NavHeader(router),
      div(
        className := "not-found-content",
        h1("404 - Sidan hittades inte"),
        p(s"Hoppsan! Sidan \"${path.split('/').last}\" finns inte."),
        img(src := randomGif, alt := "404-gif")
      ),
      Footer()
    )
