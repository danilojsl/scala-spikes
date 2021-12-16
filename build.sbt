import sbtassembly.MergeStrategy

name := "scala-spikes"
version := "0.1"
scalaVersion := "2.12.14"

val sparkVer = "3.0.2"

//For local jars dependencies
unmanagedBase := file("/home/danilo/IdeaProjects/JSL/spark-nlp/python/lib/")

lazy val sparkDependencies = Seq(
  //"com.johnsnowlabs.nlp" %% "spark-nlp" % "3.3.1",
  "org.apache.spark" %% "spark-core" % sparkVer,
  "org.apache.spark" %% "spark-mllib" % sparkVer,
)

lazy val testDependencies = Seq("org.scalatest" %% "scalatest" % "3.2.9" % "test")

libraryDependencies ++= sparkDependencies ++ testDependencies ++ Seq("net.java.dev.jna" % "jna" % "5.10.0") ++
  Seq("ai.djl.pytorch" % "pytorch-native-cpu" % "1.9.1")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

assembly / test := {}

assembly / mainClass := Some("com.pipeline.nlp.ProcessDocuments")