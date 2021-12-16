package com.pipeline.nlp

import com.johnsnowlabs.nlp.DocumentAssembler
import com.johnsnowlabs.nlp.annotators.Tokenizer
import com.johnsnowlabs.nlp.annotators.spell.context.ContextSpellCheckerApproach
import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.SparkSession

class ContextSpellChecker(spark: SparkSession) {

  import spark.implicits._

  def training() = {
    val testDataset = Seq("be a spirit for the crop, or to act as “seed” for the date or the plantain.",
      "to be or not to be, is this the question?").toDS().toDF("text")

    val assembler = new DocumentAssembler().
      setInputCol("text").
      setOutputCol("doc")

    val tokenizer = new Tokenizer()
      .setInputCols(Array("doc"))
      .setOutputCol("token")


    val spellChecker = new ContextSpellCheckerApproach()
      .setInputCols("token")
      .setOutputCol("context_spell")
      .setMinCount(1.0)
      .setLanguageModelClasses(1650)

    val pipeline = new Pipeline().setStages(Array(assembler, tokenizer, spellChecker))

    val resultDataFrame = pipeline.fit(testDataset).transform(testDataset)
    resultDataFrame.select("context_spell.result").show(false)

  }

}
