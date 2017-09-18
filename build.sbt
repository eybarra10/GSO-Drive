name := """gso-drive"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.github.mauricio" %% "mysql-async" % "0.2.21",
  "mysql" % "mysql-connector-java" % "5.1.38",
  "org.webjars" %% "webjars-play" % "2.6.1",
  "org.webjars" % "jquery-ui-themes" % "1.12.1",
  "org.webjars" % "bootstrap" % "4.0.0-beta",
  "org.webjars" % "jquery-ui" % "1.12.1",
  "org.webjars" % "momentjs" % "2.18.1",
  "org.webjars" % "jquery" % "3.2.1"
)

dependencyOverrides += "org.webjars" % "jquery" % "3.2.1"
