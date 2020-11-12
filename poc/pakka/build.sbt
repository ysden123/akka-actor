lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.2.1",
  scalaVersion := "2.13.3",
  libraryDependencies ++= {
    val akkaVersion = "2.6.10"
    val akkaHttpVersion = "10.2.1"
    val scalacticVersion = "3.2.3"
    val scalatestVersion = "3.2.3"
    val scalaLoggingVersion = "3.9.2"
    val logbackClassicVersion = "1.2.3"
    lazy val typeSafeConfVersion = "1.4.1"
    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
      "com.typesafe" % "config" % typeSafeConfVersion,
      "org.scalactic" %% "scalactic" % scalacticVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % Test
    )
  }
)

lazy val pakka = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pakka"
  )