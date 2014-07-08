package io.buildo.ingredients.logging

case class LogMessage(
  level: Level,
  message: String,
  fileName: String,
  line: Int,
  cause: Option[Throwable])
