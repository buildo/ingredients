package io.buildo.ingredients.logging

sealed trait Underlying {
  val isEnabled: PartialFunction[Level, Boolean]
  def write(level: Level,
            message: Any,
            fileName: String,
            line: Int,
            cause: Throwable = null)
}

private[logging] sealed class UnderlyingImpl(
    val name:String,
    private val transports: Seq[Transport],
    val isEnabled: PartialFunction[Level, Boolean]) extends Underlying {

  def write(level: Level,
            message: Any,
            fileName: String,
            line: Int,
            cause: Throwable = null): Unit = {
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
}
