package ingredients.logging

trait Transport {
  def write(
      name: String,
      logMessage: LogMessage): Unit
}
