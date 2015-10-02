package ingredients.jsend

import spray.json.AutoProductFormattable

package object dsl {
  def `for`[T <: Product] = new AutoProductFormattable[T] {
    def serializeOneAs(singularNameP: String) = new AutoProductFormattable[T] with JSendSingular[T] {
      val singularName = singularNameP
      def andMultipleAs(pluralNameP: String) = new AutoProductFormattable[T] with JSendSingular[T] with JSendPlural[T] {
        val singularName = singularNameP
        val pluralName = pluralNameP
      }
    }
    def serializeMultipleAs(pluralNameP: String) = new AutoProductFormattable[T] with JSendPlural[T] {
      val pluralName = pluralNameP
    }
  }
}
