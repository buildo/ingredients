package io.buildo.ingredients.logging

sealed trait Underlying {
  val isEnabled: Map[Level, Boolean]
  def write(level: Level,
            message: Any): Unit
  def write(level: Level,
            message: Any,
            cause: Throwable): Unit
  def write(level: Level,
            message: Any,
            fileName: String,
            line: Int,
            cause: Throwable = null): Unit
}

private[logging] sealed class UnderlyingImpl(
    val name:String,
    private val transports: Seq[Transport],
    isEnabledParam: PartialFunction[Level, Boolean]) extends Underlying {

  private[this] def write(level: Level,
            message: Any,
            fileName: Option[String],
            line: Option[Int],
            cause: Throwable): Unit = {
    for (transport <- transports) {
      transport.write(
        name,
        LogMessage(
          level = level,
          message = message,
          fileName = fileName,
          line = line,
          cause = Option(cause)
        ))
    }
  }

  override def write(level: Level,
                     message: Any): Unit =
    write(level, message, None, None, null)
  override def write(level: Level,
                     message: Any,
                     cause: Throwable): Unit =
    write(level, message, None, None, cause)
  override def write(level: Level,
                     message: Any,
                     fileName: String,
                     line: Int,
                     cause: Throwable = null): Unit =
    write(level, message, Some(fileName), Some(line), cause)

  override val isEnabled =
    SealedDescendants.values[Level].toList.map { l =>
      l -> isEnabledParam.applyOrElse(l, (_: Level) => false)
    }.toMap
}
