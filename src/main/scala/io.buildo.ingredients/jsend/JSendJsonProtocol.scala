package io.buildo.ingredients.jsend

import spray.json._

trait JSendJsonProtocol {
  private case class JSendResponseStructure[T](
    status: String,
    data: Option[Map[String, T]],
    message: Option[String],
    code: Option[Int])

  import DefaultJsonProtocol._
  private def JSendResponseStructureFormat[T: JsonFormat] =
    jsonFormat4(JSendResponseStructure[T])
  private val successText = "success"
  private val errorText = "error"

  private implicit def UnitFormat: JsonFormat[Nothing] = null

  implicit def JSendResponseFormat[T: JsonFormat] =
    new RootJsonFormat[JSendResponse[T]] {
      def read(json: spray.json.JsValue): JSendResponse[T] =
        JSendResponseStructureFormat[T].read(json) match {
          case JSendResponseStructure(`successText`, None, None, None) => JSendEmptySuccess
          case JSendResponseStructure(`successText`, data, None, None) => data match {
            case Some(map) => map.keys.toList match {
              case List(name) => JSendSuccess[T](name, map(name))
              case _ => throw new DeserializationException("Invalid jsend document")
            }
            case None => JSendEmptySuccess
          }
          case JSendResponseStructure(`errorText`, None, Some(message), code) =>
            JSendError(message, code)
          case _ => throw new DeserializationException("Invalid jsend document")
        }

      def write(obj: JSendResponse[T]): spray.json.JsValue =
        JSendResponseStructureFormat[T].write(obj match {
          case JSendSuccess(name, data) =>
            JSendResponseStructure[T](successText, Some(Map(name -> data)), None, None)
          case JSendEmptySuccess =>
            JSendResponseStructure[T](successText, None, None, None)
          case JSendError(message, code) =>
            JSendResponseStructure[T](errorText, None, Some(message), code)
        })

    }

  implicit def JSendSuccessFormat[T: JsonFormat]: RootJsonFormat[JSendSuccess[T]] =
    JSendResponseFormat[T].asInstanceOf[RootJsonFormat[JSendSuccess[T]]]
  implicit def JSendEmptySuccessFormat: RootJsonFormat[JSendEmptySuccess.type] =
    JSendResponseFormat[Nothing].asInstanceOf[RootJsonFormat[JSendEmptySuccess.type]]
  implicit def JSendErrorFormat: RootJsonFormat[JSendError] =
    JSendResponseFormat[Nothing].asInstanceOf[RootJsonFormat[JSendError]]
}

object JSendJsonProtocol extends JSendJsonProtocol
