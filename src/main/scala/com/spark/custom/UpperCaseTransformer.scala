package com.spark.custom

import org.apache.spark.ml.Transformer
import org.apache.spark.ml.param.{Param, ParamMap}
import org.apache.spark.ml.param.shared.{HasInputCol, HasOutputCol}
import org.apache.spark.ml.util.Identifiable
import org.apache.spark.sql.functions.{col, upper}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset}

class UpperCaseTransformer(override val uid: String) extends Transformer
  with HasInputCol
  with HasOutputCol
{

  def this() = this(Identifiable.randomUID("UPPER_CASE"))

  def setOutputCol(value: String): this.type = set(outputCol, value)

  def setInputCol(value: String): this.type = set(inputCol, value)

  override def transform(dataset: Dataset[_]): DataFrame = {
    val text = s"${$(inputCol)}.result"

    dataset.withColumn("text", col(text)(0))
      .withColumn($(outputCol), upper(col("text")))
  }

  override def copy(extra: ParamMap): Transformer = defaultCopy(extra)

  override def transformSchema(schema: StructType): StructType = {
    val outputFields = schema.fields :+ StructField($(outputCol), StringType)
    StructType(outputFields)
  }

}
