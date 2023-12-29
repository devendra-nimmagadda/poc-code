
// The simplest possible sbt build file is just one line:

scalaVersion := "2.13.8"

name := "cats-effects"
version := "1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.5.3"
