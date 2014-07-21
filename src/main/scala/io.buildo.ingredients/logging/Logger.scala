package io.buildo.ingredients.logging

import scala.reflect.runtime.universe._
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

class Logger private[logging] (val underlying: Underlying) {

  def error(message: Any): Unit = macro LoggerMacro.errorMessage
  def error(message: Any, cause: Throwable): Unit = macro LoggerMacro.errorMessageCause

  def warn(message: Any): Unit = macro LoggerMacro.warnMessage
  def warn(message: Any, cause: Throwable = null): Unit = macro LoggerMacro.warnMessageCause

  def info(message: Any): Unit = macro LoggerMacro.infoMessage
  def info(message: Any, cause: Throwable = null): Unit = macro LoggerMacro.infoMessageCause

  def debug(message: Any): Unit = macro LoggerMacro.debugMessage
  def debug(message: Any, cause: Throwable = null): Unit = macro LoggerMacro.debugMessageCause

}

object Logger {
  def apply(
      name: String,
      transports: Seq[Transport],
      isEnabled: PartialFunction[Level, Boolean]): Logger =
    new Logger(new UnderlyingImpl(name, transports, isEnabled))

  def nameOf[T: c.WeakTypeTag](c: Context) : c.Expr[String] = {
    import c.universe._
    val tpe = weakTypeOf[T]
    val name = tpe.toString
    c.Expr[String] { q"$name" }
  }
}
