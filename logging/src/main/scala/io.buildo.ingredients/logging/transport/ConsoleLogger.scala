package io.buildo.ingredients.logging.transport

import io.buildo.ingredients.logging._

import java.util.Calendar
import java.util.TimeZone
import java.text.SimpleDateFormat

class Console extends Transport {
  private[this] val timeZone = TimeZone.getTimeZone("UTC");
  private[this] val curTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
  curTimeFormat.setTimeZone(timeZone);

  def write(
      name: String,
      msg: LogMessage): Unit = {
    val today = Calendar.getInstance.getTime
    val timeString = curTimeFormat.format(today)
    val fileLineSegment = (for {
      fileName <- msg.fileName
      line <- msg.line
    } yield s" [${fileName}:${line}]").getOrElse("")
    println(s"[${msg.level}] [$timeString] [$name]${fileLineSegment} ${msg.message}")
    msg.cause map (_.printStackTrace)
  }
}
