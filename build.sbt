
val baseSettings = Seq(
  name := "scavel",

  version := "0.2.6",

  scalaVersion := "2.12.3",

  libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0",
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.5.3" % "test",

  organizationName := "Kirill Lonhus",
  organization := "ru.harati",
  crossPaths := true,
  crossScalaVersions := Seq("2.10.4", "2.11.7", "2.12.1"),

  licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("https://github.com/harati/scavel")),

  organizationHomepage := Some(url("https://github.com/harati")),
  scmInfo := Some(ScmInfo(
    browseUrl = url("https://github.com/harati/scavel"),
    connection = "scm:git://github.com/harati/scavel.git"
  )),

  // Seems that SBT key `developers` is producing incorrect results
  pomExtra := {
    <developers>
      <developer>
        <id>harati</id>
        <name>Kirill Lonhus</name>
        <url>https://github.com/harati</url>
      </developer>
    </developers>
  },

  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },

  testFrameworks := Seq(new TestFramework("utest.runner.Framework"))
)

lazy val scavel = _root_.sbtcrossproject.CrossPlugin.autoImport.crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .settings(baseSettings)
  .nativeSettings(
    scalaVersion := "2.11.11",
    nativeLinkStubs := true
  )

lazy val scavelJVM = scavel.jvm
lazy val scavelJS = scavel.js
lazy val scavelNative = scavel.native
