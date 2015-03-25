package io.buildo.ingredients.logging

import scala.annotation.switch
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

private object LoggerMacro {

  type LoggerContext = Context

  // FIXME: this should be called directly instead of being a quasi-quote
  def isEnabledDefault(c: LoggerContext) = {
    import c.universe._
    q"{ _: io.buildo.ingredients.logging.Level => false }"
  }

  def errorMessage(c: LoggerContext)(message: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled.applyOrElse(io.buildo.ingredients.logging.Level.Error, ${isEnabledDefault(c)})) $underlying.write(io.buildo.ingredients.logging.Level.Error, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line})"
  }

  def errorMessageCause(c: LoggerContext)(message: c.Tree, cause: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled.applyOrElse(io.buildo.ingredients.logging.Level.Error, ${isEnabledDefault(c)})) $underlying.write(io.buildo.ingredients.logging.Level.Error, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line}, $cause)"
  }

  def warnMessage(c: LoggerContext)(message: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled.applyOrElse(io.buildo.ingredients.logging.Level.Warn, ${isEnabledDefault(c)})) $underlying.write(io.buildo.ingredients.logging.Level.Warn, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line})"
  }

  def warnMessageCause(c: LoggerContext)(message: c.Tree, cause: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled.applyOrElse(io.buildo.ingredients.logging.Level.Warn, ${isEnabledDefault(c)})) $underlying.write(io.buildo.ingredients.logging.Level.Warn, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line}, $cause)"
  }

  def infoMessage(c: LoggerContext)(message: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled.applyOrElse(io.buildo.ingredients.logging.Level.Info, ${isEnabledDefault(c)})) $underlying.write(io.buildo.ingredients.logging.Level.Info, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line})"
  }

  def infoMessageCause(c: LoggerContext)(message: c.Tree, cause: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled.applyOrElse(io.buildo.ingredients.logging.Level.Info, ${isEnabledDefault(c)})) $underlying.write(io.buildo.ingredients.logging.Level.Info, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line}, $cause)"
  }

  def debugMessage(c: LoggerContext)(message: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled.applyOrElse(io.buildo.ingredients.logging.Level.Debug, ${isEnabledDefault(c)})) $underlying.write(io.buildo.ingredients.logging.Level.Debug, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line})"
  }

  def debugMessageCause(c: LoggerContext)(message: c.Tree, cause: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled.applyOrElse(io.buildo.ingredients.logging.Level.Debug, ${isEnabledDefault(c)})) $underlying.write(io.buildo.ingredients.logging.Level.Debug, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line}, $cause)"
  }
}
