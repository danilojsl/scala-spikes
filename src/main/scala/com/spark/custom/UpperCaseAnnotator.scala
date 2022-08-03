package com.spark.custom

import com.johnsnowlabs.nlp.AnnotatorType.DOCUMENT
import com.johnsnowlabs.nlp.{Annotation, AnnotatorModel, HasSimpleAnnotate}

class UpperCaseAnnotator(override val uid: String) extends AnnotatorModel[UpperCaseAnnotator]
  with HasSimpleAnnotate[UpperCaseAnnotator]
{
  override val inputAnnotatorTypes: Array[String] = Array(DOCUMENT)
  override val outputAnnotatorType: AnnotatorType = DOCUMENT

  override def annotate(annotations: Seq[Annotation]): Seq[Annotation] = {
    Seq()
  }

  //TODO: Some interface that wraps Annotator complexity and just work with array of strings
  /** Structural Design Patterns candidates:
   * Adapter (use when there are incompatible issues)
   * Decorator (i.e stackable traits, extends functionality)
   * Facade (wrapper to simplify)
   * Proxy (interface to other objects or provide additional functionality)
   * * */


}
