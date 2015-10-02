package ingredients.logging

case class LogMessage(
  level: Level,
  message: Any,
  fileName: Option[String],
  line: Option[Int],
  cause: Option[Throwable])
