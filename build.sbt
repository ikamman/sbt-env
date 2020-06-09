val `sbt-env` = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-env",
    version := "0.0.9-SNAPSHOT",
    organization := "com.github.ikamman"
  )
