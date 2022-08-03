package com.spark

import com.johnsnowlabs.nlp.DocumentAssembler
import com.johnsnowlabs.nlp.annotators.Tokenizer
import com.johnsnowlabs.nlp.base.LightPipeline
import com.johnsnowlabs.nlp.util.io.ResourceHelper
import com.johnsnowlabs.util.PipelineModels
import com.spark.custom.UpperCaseTransformer
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.sql.SparkSession

object MainSpark {

  val spark: SparkSession = ResourceHelper.spark

  def main(args: Array[String]): Unit = {
    val text = "Peter Parker is a nice lad and lives in New York"
//    transformWithPipeline(text)
    transformWithLightPipeline(text)
  }

  def transformWithPipeline(text: String): Unit = {
    import spark.implicits._

    val dataFrame = Seq(text).toDS().toDF("text")
    val pipelineModel = getUpperCasePipeline
    val result = pipelineModel.transform(dataFrame)

    result.show(false)
  }

  def transformWithLightPipeline(text: String): Unit = {
    val pipelineModel = getUpperCasePipeline
    val lightPipeline = new LightPipeline(pipelineModel)
    lightPipeline.setIgnoreUnsupported(false)
    val result = lightPipeline.fullAnnotate(text)
    println("result")
  }

  private def getUpperCasePipeline: PipelineModel = {
    val emptyDataFrame = PipelineModels.dummyDataset
    val documentAssembler = new DocumentAssembler().setInputCol("text").setOutputCol("document")
    //    val tokenizer = new Tokenizer().setInputCols("document").setOutputCol("token")
    val upperCase = new UpperCaseTransformer().setInputCol("document").setOutputCol("upper")

    val pipeline = new Pipeline().setStages(Array(documentAssembler, upperCase))

    pipeline.fit(emptyDataFrame)
  }

}
