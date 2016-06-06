name := """hunmin"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  javaJdbc,
  cache,
  ws,
  evolutions,
  "org.xerial" % "sqlite-jdbc" % "3.8.11.2",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.typesafe.play" %% "play-slick" % "2.0.0"
)

PlayKeys.devSettings := Seq("play.server.http.port" -> "8080")

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
