package pgkn.services

import com.raquo.laminar.api.L.*
import org.scalajs.dom

enum Theme:
  case Light, Dark

  def toAttribute: String = this match
    case Light => "light"
    case Dark  => "dark"

object ThemeService:
  private val themeVar = Var(loadInitialTheme())
  val theme: Signal[Theme] = themeVar.signal

  def toggle(): Unit =
    val newTheme =
      if themeVar.now() == Theme.Light then Theme.Dark else Theme.Light
    setTheme(newTheme)

  def initialize(): Unit =
    setTheme(themeVar.now())

  private def setTheme(theme: Theme): Unit =
    themeVar.set(theme)
    dom.document.documentElement.setAttribute("data-theme", theme.toAttribute)
    dom.window.localStorage.setItem("theme", theme.toAttribute)

  private def loadInitialTheme(): Theme =
    Option(dom.window.localStorage.getItem("theme")) match
      case Some("dark")  => Theme.Dark
      case Some("light") => Theme.Light
      case _ =>
        val prefersDark =
          dom.window.matchMedia("(prefers-color-scheme: dark)").matches
        if prefersDark then Theme.Dark else Theme.Light
