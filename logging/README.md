# Ingredients Logging

[ ![Download](https://api.bintray.com/packages/buildo/maven/ingredients-logging/images/download.svg) ](https://bintray.com/buildo/maven/ingredients-logging/_latestVersion)

An unopinionated, lightweight and flexible macro-based logging library

## Prerequisites

- Scala 2.11

## Install
Add the buildo/maven Bintray resolver and the dependency to your `build.sbt`

```scala
resolvers += "bintray buildo/maven" at "http://dl.bintray.com/buildo/maven"
libraryDependencies += "io.buildo" %% "ingredients-logging" % "..."
```

## Using the logger

```scala
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
```

## Defining a custom `Transport`
The `Transport` trait defines a single method `write` that determines how to
write a log entry

```scala
def write(name: String, logMessage: LogMessage): Unit
```

In order to provide a custom `Transport` you have to extend the `Transport` trait
and provide an implementation for the above method.

Ingredients logging comes with a console log implementation, which you can use as a reference

```scala
class Console extends Transport {
  private val curTimeFormat = new SimpleDateFormat("HH:mm")
    def write(name: String, msg: LogMessage): Unit = {
    val today = Calendar.getInstance.getTime
    val timeString = curTimeFormat.format(today)
    println(s"[${msg.level}] [$timeString] [$name] [${msg.fileName}:${msg.line}] ${msg.message}")
    msg.cause map (_.printStackTrace)
  }
}
```
## Available Transports

  - [slf4j](https://github.com/buildo/ingredients-logging-slf4j-transport)

If you want your custom Transport listed here, write to we@buildo.io

## Credits
Freely inspired by [scala-logging](https://github.com/typesafehub/scala-logging)
