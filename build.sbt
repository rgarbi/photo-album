name := "photo-album"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"
)     

play.Project.playScalaSettings
