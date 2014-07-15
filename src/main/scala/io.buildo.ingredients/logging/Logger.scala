package io.buildo.ingredients.logging

import scala.reflect.runtime.universe._
import scala.language.experimental.macros
import scala.reflect.macros.Context

private[logging] class Logger (val underlying: Underlying) {

  def error(message: String): Unit = macro LoggerMacro.errorMessage
  def error(message: String, cause: Throwable): Unit = macro LoggerMacro.errorMessageCause

  def warn(message: String): Unit = macro LoggerMacro.warnMessage
  def warn(message: String, cause: Throwable = null): Unit = macro LoggerMacro.warnMessageCause

  def info(message: String): Unit = macro LoggerMacro.infoMessage
  def info(message: String, cause: Throwable = null): Unit = macro LoggerMacro.infoMessageCause

  def debug(message: String): Unit = macro LoggerMacro.debugMessage
  def debug(message: String, cause: Throwable = null): Unit = macro LoggerMacro.debugMessageCause

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
