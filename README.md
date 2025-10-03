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
- Page components: Each exports an `element(router)` function for dependency injection
- Components: Reusable UI elements that accept router parameter for navigation

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
