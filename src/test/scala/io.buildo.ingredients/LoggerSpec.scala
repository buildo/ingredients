package io.buildo.ingredients.logging

import org.mockito.Matchers._
import org.mockito.Mockito._
import org.mockito.Matchers.{ eq => eqm }
import org.scalatest.{ Matchers, WordSpec }
import org.scalatest.mock.MockitoSugar

class LoggerSpec extends WordSpec with Matchers with MockitoSugar {

  "Calling any log method" should {
    "never call underlying methods when no levels are enabled" in {
      val f = fixture(PartialFunction.empty)
      import f._

      logger.debug(msg)
      logger.debug(msg, cause)
      logger.info(msg)
      logger.info(msg, cause)
      logger.warn(msg)
      logger.warn(msg, cause)
      logger.error(msg)
      logger.error(msg, cause)

      verify(underlying, never).write(any[Level], anyString, anyString, anyInt, any[Throwable])
    }
  }

  "Calling debug with a message" should {

    "call the underlying write method with Debug level if the debug level is enabled" in {
      val f = fixture({ case Level.Debug => true })
      import f._
      logger.debug(msg)
      verify(underlying).write(
        eqm(Level.Debug), // level must be Debug
        eqm(msg), // message should be msg
        anyString, // file name can be anything
        anyInt, // line can be anything
        eqm(null) // cause should be null
      )
    }

  }

  "Calling debug with a message and a cause" should {

    "call the underlying write method with Debug level if the debug level is enabled" in {
      val f = fixture({ case Level.Debug => true })
      import f._
      logger.debug(msg, cause)
      verify(underlying).write(
        eqm(Level.Debug), // level must be Debug
        eqm(msg), // message should be msg
        anyString, // file name can be anything
        anyInt, // line can be anything
        eqm(cause) // cause should be null
      )
    }

  }

  "Calling info with a message" should {

    "call the underlying write method with Info level if the info level is enabled" in {
      val f = fixture({ case Level.Info => true })
      import f._
      logger.info(msg)
      verify(underlying).write(
        eqm(Level.Info), // level must be Info
        eqm(msg), // message should be msg
        anyString, // file name can be anything
        anyInt, // line can be anything
        eqm(null) // cause should be null
      )
    }

  }

  "Calling info with a message and a cause" should {

    "call the underlying write method with Info level if the info level is enabled" in {
      val f = fixture({ case Level.Info => true })
      import f._
      logger.info(msg, cause)
      verify(underlying).write(
        eqm(Level.Info), // level must be Info
        eqm(msg), // message should be msg
        anyString, // file name can be anything
        anyInt, // line can be anything
        eqm(cause) // cause should be null
      )
    }

  }

  "Calling warn with a message" should {

    "call the underlying write method with Warn level if the warn level is enabled" in {
      val f = fixture({ case Level.Warn => true })
      import f._
      logger.warn(msg)
      verify(underlying).write(
        eqm(Level.Warn), // level must be Warn
        eqm(msg), // message should be msg
        anyString, // file name can be anything
        anyInt, // line can be anything
        eqm(null) // cause should be null
      )
    }

  }

  "Calling warn with a message and a cause" should {

    "call the underlying write method with Warn level if the warn level is enabled" in {
      val f = fixture({ case Level.Warn => true })
      import f._
      logger.warn(msg, cause)
      verify(underlying).write(
        eqm(Level.Warn), // level must be Warn
        eqm(msg), // message should be msg
        anyString, // file name can be anything
        anyInt, // line can be anything
        eqm(cause) // cause should be null
      )
    }

  }

  "Calling error with a message" should {

    "call the underlying write method with Error level if the error level is enabled" in {
      val f = fixture({ case Level.Error => true })
      import f._
      logger.error(msg)
      verify(underlying).write(
        eqm(Level.Error), // level must be Error
        eqm(msg), // message should be msg
        anyString, // file name can be anything
        anyInt, // line can be anything
        eqm(null) // cause should be null
      )
    }

  }

  "Calling error with a message and a cause" should {

    "call the underlying write method with Error level if the error level is enabled" in {
      val f = fixture({ case Level.Error => true })
      import f._
      logger.error(msg, cause)
      verify(underlying).write(
        eqm(Level.Error), // level must be Error
        eqm(msg), // message should be msg
        anyString, // file name can be anything
        anyInt, // line can be anything
        eqm(cause) // cause should be null
      )
    }

  }

  def fixture(isEnabled: PartialFunction[Level, Boolean]) =
    new {
      val msg = "message"
      val fileName = "LoggerSpec.scala"
      val cause = new RuntimeException("cause")
      val name = "logger"

      val underlying = mock[Underlying]
      when(underlying.isEnabled).thenReturn(isEnabled)

      val transport = mock[io.buildo.ingredients.logging.Transport]
      val logger = spy(Logger(name, Seq(transport), PartialFunction.empty))
      when(logger.underlying).thenReturn(underlying)
    }
}
