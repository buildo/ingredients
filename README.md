# Ingredients Logging
An unopinionated, lightweight and flexible macro-based logging library

![](http://i.imgur.com/P5oynmA.jpg)

## Prerequisites

- Scala 2.11

## Install
Add this to your `build.sbt`


    libraryDependencies += "io.buildo" %% "ingredients-logging" % "0.1-SNAPSHOT"

## Using Ingredients

    import io.buildo.ingredients.logging._
    import scala.language.experimental.macros

    val transports = Seq(new transport.Console())
    def logger(name: String): Logger = Logger(name, transports, {
      case Level.Info => true
    })
    def of[T]: String = macro io.buildo.ingredients.logging.Logger.nameOf[T]

    private val log = logger(of[MyAwesomeModule])
    log.debug("wow, such debugging, very unopinionated")
    log.info("wow, such info, very important, much log")

## Defining a custom `Transport`
The `Transport` typeclass defines a single method `write` that determines how to
write a log entry

    def write(name: String, logMessage: LogMessage): Unit

In order to provide a custom `Transport` you have to extend the `Transport` trait
and provide an implementation for the above method.

Ingredients comes with a console log implementation, which you can use as a reference

    class Console extends Transport {
      private val curTimeFormat = new SimpleDateFormat("HH:mm")
      def write(name: String, msg: LogMessage): Unit = {
        val today = Calendar.getInstance.getTime
        val timeString = curTimeFormat.format(today)
        println(s"[${msg.level}] [$timeString] [$name] [${msg.fileName}:${msg.line}] ${msg.message}")
      }
    }
