package ingredients

package jsend {
  trait JSendSingular[T] { val singularName: String }
  trait JSendPlural[T] { val pluralName: String }

  sealed trait JSendResponse[+T]
  case class JSendSuccess[T](name: String, data: T) extends JSendResponse[T]
  case object JSendEmptySuccess extends JSendResponse[Nothing]
  case class JSendError(message: String, code: Option[Int] = None) extends JSendResponse[Nothing]
  
  // typeclass
  trait JSendable[T] {
    def toJSendSuccess(t: T): JSendSuccess[T]
  }

  trait JSendSupport {
    implicit def jSendableForSingular[T](implicit singular: JSendSingular[T]): JSendable[T] =
      new JSendable[T] {
        def toJSendSuccess(t: T): JSendSuccess[T] =
          JSendSuccess[T](name = singular.singularName, data = t)
      }
    implicit def jSendableForPlural[T](implicit singular: JSendPlural[T]): JSendable[List[T]] =
      new JSendable[List[T]] {
        def toJSendSuccess(t: List[T]): JSendSuccess[List[T]] =
          JSendSuccess[List[T]](name = singular.pluralName, data = t)
      }
  }

  object JSendable extends JSendSupport
}

