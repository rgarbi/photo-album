name := "photo-album"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
  "org.jasypt" % "jasypt" % "1.9.1",
  "com.lambdaworks" % "scrypt" % "1.4.0"
)     

play.Project.playScalaSettings
