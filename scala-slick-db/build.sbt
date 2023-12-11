import Dependencies._

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "scala-slick-db",
    libraryDependencies += munit % Test,
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.4.1",
      "com.h2database" % "h2" % "2.2.220",



      "org.scalactic" %% "scalactic" % "3.2.17",
      "org.scalatest" %% "scalatest" % "3.2.17" % "test",
      "org.mockito" %% "mockito-scala" % "1.17.30"

    )
  )
