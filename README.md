# PGKN

ScalaJS-based community hub for EDAB05, also known as pgk or introprog.

This project uses [Vite](https://vite.dev/) as the build tool and development server for fast hot-reloading and optimized builds. Vite provides excellent development experience with instant server start and [lightning-fast HMR](https://vite.dev/guide/features.html#hot-module-replacement).

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