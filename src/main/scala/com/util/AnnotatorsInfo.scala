package com.util

import java.io.{File, PrintWriter}
import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime.{universe => ru}

object AnnotatorsInfo {

  val directoryPath = "/home/danilo/IdeaProjects/JSL/spark-nlp/src/main/scala/com/johnsnowlabs/nlp/annotators/classifier/dl"
  val fullyQualifiedName = "com.johnsnowlabs.nlp.annotators.classifier.dl."


  def main(args: Array[String]): Unit = {
    val annotatorsData: ArrayBuffer[(String, String)] = ArrayBuffer()

    // Get the list of files in the directory
    val directory = new File(directoryPath)
    val files = directory.listFiles().filter(_.isFile)

    // Loop over the files
    for (file <- files) {
      // Extract the file name without extension
      val fileName = file.getName
      val className = fileName.stripSuffix(".scala")

      val methodName = "defaultModelName"
      // Load the class dynamically
      try {

        val mirror = ru.runtimeMirror(getClass.getClassLoader)
        val module = mirror.staticModule(fullyQualifiedName + className)
        val obj = mirror.reflectModule(module)
        val cls = mirror.reflect(obj.instance)

        // Invoke a method of the class dynamically using reflection
        val method = cls.symbol.typeSignature.member(ru.TermName(methodName)).asMethod
        val defaultModelName = cls.reflectMethod(method)()
        val result: Option[String] = defaultModelName match {
          case Some(str: String) => Some(str)
          case _ => None
        }

        annotatorsData.append((className, result.getOrElse("unknown")))
      } catch {
        case ex: Throwable =>
          // Print the error as an informational message
          println(s"Error invoking method '$methodName' on class '$fullyQualifiedName': ${ex.getMessage}")
      }

      val writer = new PrintWriter("/home/danilo/tmp/annotators_model_names_v2.csv")

      // Write the header
      writer.println("Annotator,DefaultModelName")

      // Write the data
      annotatorsData.foreach { case (name, role) =>
        writer.println(s"$name,$role")
      }

      // Close the writer
      writer.close()

    }
  }

}
