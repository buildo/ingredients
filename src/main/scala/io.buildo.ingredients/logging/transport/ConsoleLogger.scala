package io.buildo.ingredients.logging.transport

import io.buildo.ingredients.logging._

import java.util.Calendar
import java.text.SimpleDateFormat

class Console extends Transport {
  private val curTimeFormat = new SimpleDateFormat("HH:mm")
  def write(
      name: String,
      msg: LogMessage): Unit = {
    val today = Calendar.getInstance.getTime
    val timeString = curTimeFormat.format(today)
    println(s"[${msg.level}] [$timeString] [$name] [${msg.fileName}:${msg.line}] ${msg.message}")
  }
}
