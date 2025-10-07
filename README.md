<p align="center">
  <img src="public/favicon.svg" alt="PGKN Logo" width="120" height="120">
</p>

# PGKN

ScalaJS-based community web hub for Lund Faculty of Engineering course EDAB05, also known as pgk or introprog.

This project uses [Vite](https://vite.dev/) as the build tool and development server for fast hot-reloading and optimized builds. Vite provides excellent development experience with instant server start and [lightning-fast HMR](https://vite.dev/guide/features.html#hot-module-replacement).

## Project Structure

### Source Code

```
src/main/scala/pgkn/
├── Main.scala              # Application entry point
├── Router.scala            # Central routing configuration and page definitions
├── components/             # Shared UI components
│   └── NavHeader.scala
│   └── ...
└── pages/                  # Individual page components
    ├── Home.scala
    └── ...
```

**Key files:**
- `Router.scala`: Defines all pages as case objects and manages routing logic with Waypoint
- Page objects: Each has an `apply(router)` method that returns an `HtmlElement`
- Component objects: Reusable UI elements with `apply()` methods; router is passed only when navigation is needed

### Styling

```
styles/
├── main.css                # Main stylesheet, imports domain-level indexes
├── components/
│   ├── index.css           # Imports all component stylesheets
│   └── nav-header.css      # Individual component styles
└── pages/
    ├── index.css           # Imports all page stylesheets
    ├── home.css            # Individual page styles
    └── sigrid.css
```

The CSS follows a Feature-Sliced Design pattern where domain index files aggregate individual stylesheets, keeping the main CSS file clean and organized. 

## Development

### Prerequisites

- [SBT](https://www.scala-sbt.org/) installed on your system
- [Node.js](https://nodejs.org/en) installed on your system.

### First time setup

Run the following to command to install the Node.js dependencies:

```bash
npm install
```

### Development workflow
Run these commands in separate terminals:

1. **Scala compilation** (watches for changes):
   ```bash
   sbt "~fastLinkJS"
   ```

2. **Development server**:
   ```bash
   npm run dev
   ```

### Build

Vite simplifies the otherwise complex building process. Simply run the following command:

```bash
npm run build
```

The built web application can now be found inside the `dist` subdirectory.

## Documentation

### Scala.js

[Scala.js](https://www.scala-js.org/) compiles Scala code to JavaScript, allowing you to write type-safe frontend applications in Scala.

**Key points:**
- Write Scala, get optimized JavaScript output
- Strong typing catches errors at compile time
- `scalajs-dom` library provides typed DOM APIs

**Example:**
```scala
import org.scalajs.dom
dom.console.log("Hello from Scala.js!")
```

### Laminar

[Laminar](https://laminar.dev/) is a functional reactive UI library for building web interfaces.

**Core concepts:**
- **Elements**: Created with functions like `div()`, `button()`, `h1()`
- **Modifiers**: Add attributes/styles with `:=` (static) or `<--` (reactive)
- **Signals**: Reactive values that automatically update the UI
- **EventStreams**: Handle user interactions and events

**Example:**
```scala
val nameVar = Var("World")
div(
  input(
    placeholder := "Enter name",
    onInput.mapToValue --> nameVar
  ),
  h1(child.text <-- nameVar.signal.map(name => s"Hello, $name!"))
)
```

**Common patterns:**
- `className := "my-class"` - static CSS class
- `child <-- signal` - reactive child element
- `onClick --> observer` - event handling
- `child.text <-- signal.map(...)` - reactive text content

### Waypoint

[Waypoint](https://github.com/raquo/Waypoint) provides type-safe, composable routing.

**Key concepts:**
- Routes defined as `Route.static(PageObject, root / "path" / endOfSegments)`
- Navigation with `router.navigateTo(PageObject)`
- Current page observed via `router.currentPageSignal`
- Browser history integration built-in

**Example:**
```scala
// In Router.scala
val homeRoute = Route.static(HomePage, root / endOfSegments)

// In components
a(
  router.navigateTo(HomePage),
  "Go Home"
)
```

### Modern Scala 3 Features Used

This codebase uses Scala 3 syntax:

- **Top-level definitions**: Functions and values without wrapping objects
- **Enums and sealed hierarchies**: `sealed abstract class Page` with case objects
- **Indentation-based syntax**: No braces required
- **`@main` annotation**: Simple entry points

### Adding a New Page

1. Create `src/main/scala/pgkn/pages/NewPage.scala`:
   ```scala
   package pgkn.pages

   import com.raquo.laminar.api.L.*
   import com.raquo.waypoint.Router
   import pgkn.components.{NavHeader, Footer}

   object NewPage:
     def apply(router: Router[pgkn.Page]): HtmlElement =
       mainTag(
         className := "new-page",
         NavHeader(router),
         div(
           h1("My New Page")
         ),
         Footer()
       )
   ```

2. Add to [Router.scala](src/main/scala/pgkn/Router.scala):
   ```scala
   // Add case object
   case object NewPagePage extends Page("PGKN - New Page")

   // Add route
   val newPageRoute = Route.static(NewPagePage, root / "new-page" / endOfSegments)

   // Add to routes list
   routes = List(homeRoute, kaptenAllocRoute, sigridRoute, newPageRoute)

   // Add to splitter
   .collectStatic(NewPagePage)(NewPage(RouterInstance))
   ```

3. Optionally add navigation link in [NavHeader.scala](src/main/scala/pgkn/components/NavHeader.scala)

4. Create `styles/pages/new-page.css` and import in `styles/pages/index.css`

### Theming (Dark/Light Mode)

The application supports light and dark themes with automatic persistence and system preference detection.

**ThemeService** (`src/main/scala/pgkn/services/ThemeService.scala`):
- Manages theme state using Laminar's reactive `Var`/`Signal` pattern
- Exposes a `Signal[Theme]` for components to reactively observe theme changes
- Provides `toggle()` method to switch between themes
- Persists theme preference to localStorage
- Detects system color scheme preference on first visit

**CSS Implementation**:
- Theme-specific CSS variables are defined in `styles/themes/`
  - `light.css`: Uses `:root` selector for default light theme (loads before JS)
  - `dark.css`: Uses `[data-theme="dark"]` attribute selector
- ThemeService sets `data-theme` attribute on `<html>` element to apply dark theme
- Components use CSS variables (e.g., `var(--color-bg)`) that automatically update when theme changes



### Useful Resources

- [Laminar Documentation](https://laminar.dev/)
- [Scala.js Documentation](https://www.scala-js.org/)
- [Waypoint README](https://github.com/raquo/Waypoint)
- [Scala 3 Book](https://docs.scala-lang.org/scala3/book/introduction.html)
