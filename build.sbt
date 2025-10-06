import org.scalajs.linker.interface.ModuleSplitStyle

lazy val pgkn = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin) // Enable the Scala.js plugin in this project
  .settings(
    scalaVersion := "3.7.3",

    // Tell Scala.js that this is an application with a main method
    scalaJSUseMainModuleInitializer := true,

    /* Configure Scala.js to emit modules in the optimal way to
     * connect to Vite's incremental reload.
     * - emit ECMAScript modules
     * - emit as many small modules as possible for classes in the "pgkn" package
     * - emit as few (large) modules as possible for all other classes
     *   (in particular, for the standard library)
     */
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("pgkn")))
    },

    /* Depend on the scalajs-dom library.
     * It provides static types for the browser DOM APIs.
     */
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.1",

    // Depend on Laminar
    libraryDependencies += "com.raquo" %%% "laminar" % "17.0.0",

    // Depend on Waypoint for routing
    libraryDependencies += "com.raquo" %%% "waypoint" % "9.0.0",

    // Depend on uPickle for serialization
    libraryDependencies += "com.lihaoyi" %%% "upickle" % "4.0.2",

    // Depend on scala-java-time for date/time utilities in Scala.js
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.6.0"
  )
