package com.pipeline.nlp

import com.johnsnowlabs.nlp.DocumentAssembler
import com.johnsnowlabs.nlp.annotators.Tokenizer
import com.johnsnowlabs.nlp.embeddings.BertEmbeddings
import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.size

class BertEmbeddings(spark: SparkSession) {

  def computeEmbeddings(modelPath: String): Unit = {
    println("********** In computeEmbeddings")
    import spark.implicits._

    val dataFrame = Seq("Peter lives in Dallas", "Jon Snow lives in Winterfell").toDS().toDF("text")
    val documentAssembler = new DocumentAssembler().setInputCol("text").setOutputCol("document")
    val tokenizer = new Tokenizer().setInputCols("document").setOutputCol("token")
    val bertLoaded = BertEmbeddings.load(modelPath)
      .setInputCols("document", "token")
      .setOutputCol("bert")
      .setCaseSensitive(true)
      .setDeepLearningEngine("pytorch")

    val pipeline = new Pipeline().setStages(Array(documentAssembler, tokenizer, bertLoaded))
    val resultDataFrame = pipeline.fit(dataFrame).transform(dataFrame)
    //resultDataFrame.printSchema()
    resultDataFrame.select($"document", size($"bert.embeddings")).show(false)
  }

}
