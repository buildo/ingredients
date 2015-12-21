package ingredients.logging

import org.mockito.Matchers.{ eq => eqm, _ }
import org.mockito.Mockito._
import org.scalatest.{ Matchers, WordSpec }
import org.scalatest.mock.MockitoSugar

class PlainOldLoggerSpec extends WordSpec with Matchers with MockitoSugar {

  "PlainOldLogger" should {
    "invoke write on underlying only when the log level is enabled" in {
      val msg = "message"
      val cause = new RuntimeException("cause")

      val underlying = mock[ingredients.logging.Underlying]
      val plainOldLogger = new PlainOldLogger(underlying)

      when(underlying.isEnabled).thenReturn(Map[Level,Boolean](
        Level.Error -> true,
        Level.Info -> false
      ))

      plainOldLogger.error(msg)
      verify(underlying).write(eqm(Level.Error), eqm(msg))

      plainOldLogger.info(msg)
      verify(underlying, times(0)).write(eqm(Level.Info), any())
    }
  }

}
