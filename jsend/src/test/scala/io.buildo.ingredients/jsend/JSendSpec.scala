package ingredients.jsend

import org.scalatest.{ Matchers, WordSpec }
import spray.json._

class JSendSpec extends WordSpec with Matchers {
  def got[T](thing: T)(
    implicit format: RootJsonFormat[T]) = format.write(thing)

  "JSendJsonProtocol" should {
    "format Success" in {
      import DefaultJsonProtocol._
      import JSendJsonProtocol._

      got(JSendSuccess("thing", "asdf")) shouldBe (
        JsObject(
          "status" -> JsString("success"),
          "data" -> JsObject(
            "thing" -> JsString("asdf")
          )
        ))

      got(JSendEmptySuccess) shouldBe (
        JsObject(
          "status" -> JsString("success")
        ))

      got(JSendError("blah", Some(10))) shouldBe (
        JsObject(
          "status" -> JsString("error"),
          "message" -> JsString("blah"),
          "code" -> JsNumber(10)
        ))
    }
  }
}
