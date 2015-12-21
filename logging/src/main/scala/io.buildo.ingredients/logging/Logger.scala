package ingredients.logging

import scala.reflect.runtime.universe._
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

class PlainOldLogger private[logging] (val underlying: Underlying) {

  def error(message: Any): Unit = write(Level.Error, message)
  def error(message: Any, cause: Throwable): Unit = write(Level.Error, message, cause)

  def warn(message: Any): Unit = write(Level.Warn, message)
  def warn(message: Any, cause: Throwable): Unit = write(Level.Warn, message, cause)

  def info(message: Any): Unit = write(Level.Info, message)
  def info(message: Any, cause: Throwable): Unit = write(Level.Info, message, cause)

  def debug(message: Any): Unit = write(Level.Debug, message)
  def debug(message: Any, cause: Throwable): Unit = write(Level.Debug, message, cause)

  private def write(level: Level, message: Any): Unit =
    if (underlying.isEnabled(level)) {
      underlying.write(level, message)
    }

  private def write(level: Level, message: Any, cause: Throwable): Unit =
    if (underlying.isEnabled(level)) {
      underlying.write(level, message, cause)
    }

}

object PlainOldLogger {
  def apply(
      name: String,
      transports: Seq[Transport],
      isEnabled: PartialFunction[Level, Boolean]): PlainOldLogger =
    new PlainOldLogger(new UnderlyingImpl(name, transports, isEnabled))
}

class Logger private[logging] (val underlying: Underlying) {

  def error(message: Any): Unit = macro LoggerMacro.errorMessage
  def error(message: Any, cause: Throwable): Unit = macro LoggerMacro.errorMessageCause

  def warn(message: Any): Unit = macro LoggerMacro.warnMessage
  def warn(message: Any, cause: Throwable): Unit = macro LoggerMacro.warnMessageCause

  def info(message: Any): Unit = macro LoggerMacro.infoMessage
  def info(message: Any, cause: Throwable): Unit = macro LoggerMacro.infoMessageCause

  def debug(message: Any): Unit = macro LoggerMacro.debugMessage
  def debug(message: Any, cause: Throwable): Unit = macro LoggerMacro.debugMessageCause

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
