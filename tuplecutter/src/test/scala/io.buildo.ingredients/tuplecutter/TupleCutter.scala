package ingredients.tuplecutter

import org.scalatest.{ Matchers, WordSpec }

class TupleCutterSpec extends WordSpec with Matchers {

  "Tuple.tail" should {
    "remove first element of a 2-tuple" in {
      ("A", 2).tail shouldBe Tuple1(2)
    }
    "remove the first element of a 3-tuple" in {
      ("A", 2, "C").tail shouldBe (2, "C")
    }
    "remove the first element of a 8-tuple" in {
      ("💩", 2, "C", "d", List(), Map(1 -> "ciao"), 7, 8).tail shouldBe (
        2, "C", "d", List(), Map(1 -> "ciao"), 7, 8)
    }
  }

  "Tuple.head" should {
    "retrieve the first element of a 2-tuple" in {
      ("A", 2).head shouldBe "A"
    }
    "retrieve the first element of a 3-tuple" in {
      ("A", 2, "C").head shouldBe "A"
    }
    "retrieve the first element of a 8-tuple" in {
      ("☃", 2, "C", "d", List(), Map(1 -> "ciao"), 7, 8).head shouldBe "☃"
    }
  }
}
