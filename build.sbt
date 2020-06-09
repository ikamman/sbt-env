val `sbt-env` = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    organization := "com.github.ikamman",
    name := "sbt-env",
    (envFileName in Global) := "versions.env",
    version := envVariables.value("SBT_ENV_VERSION"),
    scalaVersion := "2.12.11",

    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    bintrayRepository := "sbt-plugins",
    publishMavenStyle := true
  )
