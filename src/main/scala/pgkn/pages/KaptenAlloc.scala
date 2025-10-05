package pgkn.pages

import com.raquo.laminar.api.L.*
import com.raquo.waypoint.*
import pgkn.components.NavHeader

object KaptenAlloc:
  def apply(router: Router[pgkn.Page]): HtmlElement =
    mainTag(
      className := "kapten-alloc-page",
      NavHeader(router),
      div(
        className := "kapten-alloc-page-content",
        div(
          className := "kapten-alloc-search",
          input(
            typ := "text",
            placeholder := "Search..."
          )
        ),
        div(
          className := "kapten-alloc-table-container",
          table(
            className := "kapten-alloc-table",
            thead(
              tr(
                th("Name"),
                th("Email"),
                th("Group"),
                th("Status")
              )
            ),
            tbody(
              tr(
                td("Alice Anderson"),
                td("alice@example.com"),
                td("Group A"),
                td("Active")
              ),
              tr(
                td("Bob Builder"),
                td("bob@example.com"),
                td("Group B"),
                td("Active")
              ),
              tr(
                td("Charlie Chen"),
                td("charlie@example.com"),
                td("Group A"),
                td("Pending")
              ),
              tr(
                td("Diana Davis"),
                td("diana@example.com"),
                td("Group C"),
                td("Active")
              ),
              tr(
                td("Eve Evans"),
                td("eve@example.com"),
                td("Group B"),
                td("Inactive")
              ),
              tr(
                td("Alice Anderson"),
                td("alice@example.com"),
                td("Group A"),
                td("Active")
              ),
              tr(
                td("Bob Builder"),
                td("bob@example.com"),
                td("Group B"),
                td("Active")
              ),
              tr(
                td("Charlie Chen"),
                td("charlie@example.com"),
                td("Group A"),
                td("Pending")
              ),
              tr(
                td("Diana Davis"),
                td("diana@example.com"),
                td("Group C"),
                td("Active")
              ),
              tr(
                td("Eve Evans"),
                td("eve@example.com"),
                td("Group B"),
                td("Inactive")
              ),
              tr(
                td("Alice Anderson"),
                td("alice@example.com"),
                td("Group A"),
                td("Active")
              ),
              tr(
                td("Bob Builder"),
                td("bob@example.com"),
                td("Group B"),
                td("Active")
              ),
              tr(
                td("Charlie Chen"),
                td("charlie@example.com"),
                td("Group A"),
                td("Pending")
              ),
              tr(
                td("Diana Davis"),
                td("diana@example.com"),
                td("Group C"),
                td("Active")
              ),
              tr(
                td("Eve Evans"),
                td("eve@example.com"),
                td("Group B"),
                td("Inactive")
              ),
              tr(
                td("Alice Anderson"),
                td("alice@example.com"),
                td("Group A"),
                td("Active")
              ),
              tr(
                td("Bob Builder"),
                td("bob@example.com"),
                td("Group B"),
                td("Active")
              ),
              tr(
                td("Charlie Chen"),
                td("charlie@example.com"),
                td("Group A"),
                td("Pending")
              ),
              tr(
                td("Diana Davis"),
                td("diana@example.com"),
                td("Group C"),
                td("Active")
              ),
              tr(
                td("Eve Evans"),
                td("eve@example.com"),
                td("Group B"),
                td("Inactive")
              )
            )
          )
        )
      ),
      div(
        className := "kapten-alloc-controls",
        p("Controls will go here")
      )
    )
