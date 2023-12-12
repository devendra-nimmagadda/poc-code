
ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))

  .settings(
    name := "akka-actors",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.8.4",
      "com.typesafe.akka" %% "akka-actor" % "2.8.4",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.8.4",
      //"com.typesafe.akka" %% "akka-slf4j" % "2.8.4",
      "ch.qos.logback" %  "logback-classic" % "1.3.11"

    )
  )
