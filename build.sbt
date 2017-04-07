name := "scavel"

version := "0.2.4"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
organizationName := "Kirill Lonhus"
organization := "ru.harati"
crossPaths := true
crossScalaVersions := Seq("2.10.4", "2.11.7", "2.12.1")

licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/harati/scavel"))

organizationHomepage := Some(url("https://github.com/harati"))
scmInfo := Some(ScmInfo(
    browseUrl = url("https://github.com/harati/scavel"),
    connection = "scm:git://github.com/harati/scavel.git"
))

  // Seems that SBT key `developers` is producing incorrect results
pomExtra := {
  <developers>
    <developer>
      <id>harati</id>
      <name>Kirill Lonhus</name>
      <url>https://github.com/harati</url>
    </developer>
  </developers>
}

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}



    