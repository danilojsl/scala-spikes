import com.pipeline.nlp.NamedEntityRecognition
import org.scalatest.flatspec.AnyFlatSpec

class NameEntityRecognitionTest extends AnyFlatSpec {

  "NameEntityRecognition" should "tag entities" in {
    val nameEntityRecognition = new NamedEntityRecognition("src/test/resources/test_docs.csv",
      "src/test/resources/ner")
    nameEntityRecognition.annotateEntities()
  }

}
