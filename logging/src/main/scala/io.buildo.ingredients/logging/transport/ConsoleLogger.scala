package io.buildo.ingredients.logging.transport

import io.buildo.ingredients.logging._

import java.util.Calendar
import java.util.TimeZone
import java.text.SimpleDateFormat

import scala.{Console => C}

class Console(colorized: Boolean = false) extends Transport {
  private[this] val timeZone = TimeZone.getTimeZone("UTC");
  private[this] val curTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
    val level = colored(C.BLUE, msg.level)
    val time = colored(C.YELLOW, timeString)
    val nameMsg = colored(C.WHITE, name)
    println(s"[$level] [$timeString] [$nameMsg]${fileLineSegment} ${msg.message}")
    msg.cause map (_.printStackTrace)
  }

  private def colored(color: String, s: String) = if (colorized) s"$color$s${C.RESET}" else s

}
