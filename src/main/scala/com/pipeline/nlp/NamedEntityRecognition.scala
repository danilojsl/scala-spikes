package com.pipeline.nlp

import com.johnsnowlabs.nlp.DocumentAssembler
import com.johnsnowlabs.nlp.annotators.Tokenizer
import com.johnsnowlabs.nlp.annotators.ner.dl.NerDLModel
import com.johnsnowlabs.nlp.embeddings.WordEmbeddingsModel
import com.johnsnowlabs.nlp.util.io.ResourceHelper
import com.johnsnowlabs.util.PipelineModels
import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}

class NamedEntityRecognition(sourcePath: String, destinationPath: String) {

  def annotateEntities(): Unit = {
   val nerDataFrame = transformToEntitiesData()
    nerDataFrame.show(false)

    nerDataFrame.write.json(destinationPath)
  }

  private def transformToEntitiesData(): DataFrame = {
    val emptyDataFrame = PipelineModels.dummyDataset
    val documentsDataFrame = loadDataFrame()
    documentsDataFrame.show(false)

    val documentAssembler = new DocumentAssembler().setInputCol("text").setOutputCol("document")
    val tokenizer = new Tokenizer().setInputCols("document").setOutputCol("token")
    val embeddings = WordEmbeddingsModel.pretrained().setInputCols(Array("document", "token")).setOutputCol("embeddings")
    val nerTagger = NerDLModel.pretrained().setInputCols("document", "token", "embeddings").setOutputCol("ner")

    val pipeline = new Pipeline().setStages(Array(documentAssembler, tokenizer, embeddings, nerTagger))
    val nerDataSet = pipeline.fit(emptyDataFrame).transform(documentsDataFrame)

    nerDataSet.withColumn("entity", col("ner.result"))
      .select("id", "entity", "ner.begin", "ner.end")
  }

  private def loadDataFrame(): DataFrame = {
    val spark: SparkSession = ResourceHelper.spark

    spark.read.options(Map("header"-> "true")).csv(sourcePath)
  }

}
