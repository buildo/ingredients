package ingredients.logging

sealed abstract class Level(val name: String)

object Level {
  case object Debug extends Level("debug")
  case object Info extends Level("info")
  case object Warn extends Level("warn")
  case object Error extends Level("error")
}
