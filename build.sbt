import sbtassembly.MergeStrategy

name := "scala-spikes"
version := "0.2"
scalaVersion := "2.12.15"

val sparkVer = "3.0.2"

lazy val sparkDependencies = Seq(
  "com.johnsnowlabs.nlp" %% "spark-nlp" % "4.0.2",
  "org.apache.spark" %% "spark-core" % sparkVer,
  "org.apache.spark" %% "spark-mllib" % sparkVer,
)

lazy val testDependencies = Seq("org.scalatest" %% "scalatest" % "3.2.9" % "test")

libraryDependencies ++= sparkDependencies ++ testDependencies ++ Seq("net.java.dev.jna" % "jna" % "5.10.0")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

assembly / test := {}

assembly / mainClass := Some("com.pipeline.nlp.ProcessDocuments")