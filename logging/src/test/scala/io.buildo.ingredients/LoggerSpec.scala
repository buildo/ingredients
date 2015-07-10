package io.buildo.ingredients.logging

import org.mockito.Matchers.{ eq => eqm, _ }
import org.mockito.Mockito._
import org.mockito.ArgumentMatcher
import org.scalatest.{ Matchers, WordSpec }
import org.scalatest.mock.MockitoSugar

class LoggerSpec extends WordSpec with Matchers with MockitoSugar {

  class LogMessageMatcher(that: LogMessage) extends ArgumentMatcher[LogMessage] {
    def matches(o: Any) = o match {
      case LogMessage(level, msg, fileName, _, cause) =>
        level == that.level &&
        msg == that.message &&
        fileName == that.fileName &&
        cause == that.cause
    }
  }

  "Any value" should {
    "be allowed as message" in {
      val f = fixture({ case _ => true })
      import f._

      val list = List(1, 2, 3)
      val bool = true
      val int = 42
      val tuple = ("hello", 37)

      logger.debug(bool)
      logger.info(int)
      logger.warn(tuple)
      logger.error(list)

      val boolMsg = spy(LogMessage(Level.Debug, bool, Some(fileName), Some(42), None))
      val intMsg = spy(LogMessage(Level.Info, int, Some(fileName), Some(42), None))
      val tupleMsg = spy(LogMessage(Level.Warn, tuple, Some(fileName), Some(42), None))
      val listMsg = spy(LogMessage(Level.Error, list, Some(fileName), Some(42), None))

      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(boolMsg)))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(intMsg)))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(tupleMsg)))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(listMsg)))

      //verify(underlying).write(eqm(Level.Debug), eqm(bool.toString), anyString, anyInt, any[Throwable])
      //verify(underlying).write(eqm(Level.Info), eqm(int.toString), anyString, anyInt, any[Throwable])
      //verify(underlying).write(eqm(Level.Warn), eqm(tuple.toString), anyString, anyInt, any[Throwable])
      //verify(underlying).write(eqm(Level.Error), eqm(list.toString), anyString, anyInt, any[Throwable])
    }
  }

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

    "never call write on any transport when no levels are enabled" in {

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

      verify(transport, never).write(anyString, any[LogMessage])
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
        eqm(fileName), // file name should be fileName
        anyInt, // line can be anything
        eqm(null) // cause should be null
      )
    }

    "call write on all the registered transports with Debug level when debug level is enabled" in {
      val f = fixture({case Level.Debug => true })
      import f._
      logger.debug(msg)
      val logMsg = spy(LogMessage(Level.Debug, msg, Some(fileName), Some(42), None))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
      verify(transport2).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
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
        eqm(fileName), // file name should be fileName
        anyInt, // line can be anything
        eqm(cause) // cause should be null
      )
    }

    "call write on all the registered transports with Debug level when debug level is enabled" in {
      val f = fixture({case Level.Debug => true })
      import f._
      logger.debug(msg, cause)
      val logMsg = spy(LogMessage(Level.Debug, msg, Some(fileName), Some(42), Some(cause)))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
      verify(transport2).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
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
        eqm(fileName), // file name should be fileName
        anyInt, // line can be anything
        eqm(null) // cause should be null
      )
    }

    "call write on all the registered transports with Info level when info level is enabled" in {
      val f = fixture({case Level.Info => true })
      import f._
      logger.info(msg)
      val logMsg = spy(LogMessage(Level.Info, msg, Some(fileName), Some(42), None))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
      verify(transport2).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
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
        eqm(fileName), // file name should be fileName
        anyInt, // line can be anything
        eqm(cause) // cause should be null
      )
    }

    "call write on all the registered transports with Info level when info level is enabled" in {
      val f = fixture({case Level.Info => true })
      import f._
      logger.info(msg, cause)
      val logMsg = spy(LogMessage(Level.Info, msg, Some(fileName), Some(42), Some(cause)))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
      verify(transport2).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
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
        eqm(fileName), // file name should be fileName
        anyInt, // line can be anything
        eqm(null) // cause should be null
      )
    }

    "call write on all the registered transports with Warn level when warn level is enabled" in {
      val f = fixture({case Level.Warn => true })
      import f._
      logger.warn(msg)
      val logMsg = spy(LogMessage(Level.Warn, msg, Some(fileName), Some(42), None))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
      verify(transport2).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
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
        eqm(fileName), // file name should be fileName
        anyInt, // line can be anything
        eqm(cause) // cause should be null
      )
    }

    "call write on all the registered transports with Warn level when warn level is enabled" in {
      val f = fixture({case Level.Warn => true })
      import f._
      logger.warn(msg, cause)
      val logMsg = spy(LogMessage(Level.Warn, msg, Some(fileName), Some(42), Some(cause)))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
      verify(transport2).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
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

    "call write on all the registered transports with Error level when error level is enabled" in {
      val f = fixture({case Level.Error => true })
      import f._
      logger.error(msg)
      val logMsg = spy(LogMessage(Level.Error, msg, Some(fileName), Some(42), None))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
      verify(transport2).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
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

    "call write on all the registered transports with Error level when error level is enabled" in {
      val f = fixture({case Level.Error => true })
      import f._
      logger.error(msg, cause)
      val logMsg = spy(LogMessage(Level.Error, msg, Some(fileName), Some(42), Some(cause)))
      verify(transport).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
      verify(transport2).write(eqm(name), argThat(new LogMessageMatcher(logMsg)))
    }

  }

  def fixture(isEnabled: PartialFunction[Level, Boolean]) =
    new {
      val msg = "message"
      val fileName = "LoggerSpec.scala"
      val cause = new RuntimeException("cause")
      val name = "logger"

      val transport = mock[io.buildo.ingredients.logging.Transport]
      val transport2 = mock[io.buildo.ingredients.logging.Transport]

      var underlying = spy(new UnderlyingImpl(name, Seq(transport, transport2), isEnabled))

      val logger = spy(new Logger(underlying))
    }
}
