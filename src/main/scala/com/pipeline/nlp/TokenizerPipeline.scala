package com.pipeline.nlp

import com.johnsnowlabs.nlp.DocumentAssembler
import com.johnsnowlabs.nlp.annotators.Tokenizer
import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.SparkSession

class TokenizerPipeline(text: String, spark: SparkSession) {

  import spark.implicits._

  def annotateTokens(): Unit = {
    val textDataSet = Seq(text).toDS.toDF("text")

    val documentAssembler = new DocumentAssembler().setInputCol("text").setOutputCol("document")
    val tokenizer = new Tokenizer().setInputCols("document").setOutputCol("token")

    val pipeline = new Pipeline().setStages(Array(documentAssembler, tokenizer))
    val resultDataSet = pipeline.fit(textDataSet).transform(textDataSet)
    resultDataSet.select("token").show(false)
  }

}
