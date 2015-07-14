package io.buildo.ingredients.logging

import scala.annotation.switch
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

private object LoggerMacro {

  type LoggerContext = Context

  def errorMessage(c: LoggerContext)(message: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled(io.buildo.ingredients.logging.Level.Error)) $underlying.write(io.buildo.ingredients.logging.Level.Error, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line})"
  }

  def errorMessageCause(c: LoggerContext)(message: c.Tree, cause: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled(io.buildo.ingredients.logging.Level.Error)) $underlying.write(io.buildo.ingredients.logging.Level.Error, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line}, $cause)"
  }

  def warnMessage(c: LoggerContext)(message: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled(io.buildo.ingredients.logging.Level.Warn)) $underlying.write(io.buildo.ingredients.logging.Level.Warn, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line})"
  }

  def warnMessageCause(c: LoggerContext)(message: c.Tree, cause: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled(io.buildo.ingredients.logging.Level.Warn)) $underlying.write(io.buildo.ingredients.logging.Level.Warn, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line}, $cause)"
  }

  def infoMessage(c: LoggerContext)(message: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled(io.buildo.ingredients.logging.Level.Info)) $underlying.write(io.buildo.ingredients.logging.Level.Info, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line})"
  }

  def infoMessageCause(c: LoggerContext)(message: c.Tree, cause: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled(io.buildo.ingredients.logging.Level.Info)) $underlying.write(io.buildo.ingredients.logging.Level.Info, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line}, $cause)"
  }

  def debugMessage(c: LoggerContext)(message: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled(io.buildo.ingredients.logging.Level.Debug)) $underlying.write(io.buildo.ingredients.logging.Level.Debug, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line})"
  }

  def debugMessageCause(c: LoggerContext)(message: c.Tree, cause: c.Tree) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isEnabled(io.buildo.ingredients.logging.Level.Debug)) $underlying.write(io.buildo.ingredients.logging.Level.Debug, $message, ${c.enclosingPosition.source.toString}, ${c.enclosingPosition.line}, $cause)"
  }
}
