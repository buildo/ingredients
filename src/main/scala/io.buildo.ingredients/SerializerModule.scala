package io.buildo.ingredients

import spray.json._
import scala.util.{ Success, Failure }
import java.util.TimeZone
import scala.reflect.runtime.universe._

object JSend {
  trait JSendable[T] {
    def toJSend(x: T): JSendSuccess[T]
  }
  trait JSendSingular[T] { val singularName: String }
  trait JSendPlural[T] { val pluralName: String }

  implicit def jSendableFor[T](implicit evidence: JSendSingular[T]): JSendable[T] = new JSendable[T] {
    def toJSend(x: T) = JSendSuccess[T](data = Some(Map(evidence.singularName -> x)))
  }
  implicit def jSendableForMany[T](implicit evidence: JSendPlural[T]): JSendable[List[T]] = new JSendable[List[T]] {
    def toJSend(x: List[T]) = JSendSuccess[List[T]](data = Some(Map(evidence.pluralName -> x)))
  }

  case class JSendSuccess[T](status: String = "success", data: Option[Map[String, T]])
  case class JSendError(status: String = "error", message: String, code: Int = -1)

  class JSendException(val msg: String) extends RuntimeException(msg)

  val JSendEmptyResponse = JSendSuccess[Unit](data = None)
}


object SerializerDSL {
  import JSend._
  def `for`[T <: Product] = new AutoProductFormattable[T] {
    def serializeOneAs(singularNameP: String) = new AutoProductFormattable[T] with JSendSingular[T] {
      val singularName = singularNameP
      def andMultipleAs(pluralNameP: String) = new AutoProductFormattable[T] with JSendSingular[T] with JSendPlural[T] {
        val singularName = singularNameP
        val pluralName = pluralNameP
      }
    }
    def serializeMultipleAs(pluralNameP: String) = new AutoProductFormattable[T] with JSendPlural[T] {
      val pluralName = pluralNameP
    }
  }
}


trait JsendJsonProtocol extends AutoProductFormat {
  import JSend._
  import SerializerDSL._

  implicit val EmptyJSendable = new JSendable[Unit] {
    def toJSend(x: Unit) = JSendEmptyResponse
  }

  implicit def JSendSuccessFormat[T: JsonFormat]: RootJsonFormat[JSendSuccess[T]] =
    rootFormat(lazyFormat(jsonFormat2(JSendSuccess[T])))

  implicit def JSendErrorFormat: RootJsonFormat[JSendError] =
    rootFormat(lazyFormat(jsonFormat3(JSendError)))
}
