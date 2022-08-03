package com.pipeline.nlp

import com.johnsnowlabs.nlp.util.io.ResourceHelper
import org.apache.spark.sql.SparkSession

object ProcessDocuments {

  def main(args: Array[String]): Unit = {
    val nlpPipeline = args.head
    nlpPipeline match {
      case "NER" => processNERPipeline(args(1), args.last)
      case "BERT" => processBertEmbeddingsPipeline(args(1))
      case "CONTEXT-SPELL" => trainContextSpell()
      case "TOKENIZER" => processTokenizerPipeline()
      case _ => throw new UnsupportedOperationException(s"NLP Pipeline $nlpPipeline not supported")
    }

  }

  private def processNERPipeline(sourcePath: String, destinationPath: String): Unit = {
    println(s"Processing NER Pipeline with sourcePath: $sourcePath and destinationPath: $destinationPath")
    val nameEntityRecognition = new NamedEntityRecognition(sourcePath, destinationPath)

    nameEntityRecognition.annotateEntities()
  }

  private def processBertEmbeddingsPipeline(modelPath: String): Unit = {
    val spark: SparkSession = ResourceHelper.spark
    val bertEmbeddings = new BertEmbeddings(spark)
    bertEmbeddings.computeEmbeddings(modelPath)
  }

  private def trainContextSpell(): Unit = {
    val spark: SparkSession = ResourceHelper.spark
    val contextSpellChecker = new ContextSpellChecker(spark)
    contextSpellChecker.training()
  }

  private def processTokenizerPipeline(): Unit = {
    val spark: SparkSession = ResourceHelper.spark
    val tokenizerPipeline = new TokenizerPipeline("Hello world", spark)
    tokenizerPipeline.annotateTokens()
  }

}
