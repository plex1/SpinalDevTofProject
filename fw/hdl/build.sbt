
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.spinalhdl",
      scalaVersion := "2.11.12",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "superproject"
  ).dependsOn(vexRiscv)

//lazy val vexRiscv = RootProject(uri("git://github.com/SpinalHDL/VexRiscv.git"))

//For dependancies localy on your computer :
lazy val vexRiscv = RootProject(file("./ext/VexRiscv"))//For dependancies localy on your computer :

//If you want a specific git commit : 
//lazy val vexRiscv = RootProject(uri("git://github.com/SpinalHDL/VexRiscv.git#commitHash"))

//If the dependancy is localy on your computer : 
//lazy val vexRiscv = RootProject(file("local/path/to/the/VexRiscv/sbt/project/VexRiscv"))

libraryDependencies ++= Seq(
  "com.github.spinalhdl" % "spinalhdl-core_2.11" % "1.3.3",
  "com.github.spinalhdl" % "spinalhdl-lib_2.11" % "1.3.3",
  "org.yaml" % "snakeyaml" % "1.8"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.4.0"
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.4.0"

//addCompilerPlugin("org.scala-lang.plugins" % "scala-continuations-plugin_2.11.6" % "1.0.2")
//scalacOptions += "-P:continuations:enable"
fork := true