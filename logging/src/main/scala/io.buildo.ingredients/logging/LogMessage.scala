package io.buildo.ingredients.logging

case class LogMessage(
  level: Level,
  message: Any,
  fileName: String,
  line: Int,
  cause: Option[Throwable])
