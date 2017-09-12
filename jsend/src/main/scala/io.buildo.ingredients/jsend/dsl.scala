package ingredients.jsend

import spray.json.AutoProductFormat

package object dsl {
  def `for`[T <: Product] = new AutoProductFormat {
    def serializeOneAs(singularNameP: String) = new AutoProductFormat with JSendSingular[T] {
      val singularName = singularNameP
      def andMultipleAs(pluralNameP: String) = new AutoProductFormat with JSendSingular[T] with JSendPlural[T] {
        val singularName = singularNameP
        val pluralName = pluralNameP
      }
    }
    def serializeMultipleAs(pluralNameP: String) = new AutoProductFormat with JSendPlural[T] {
      val pluralName = pluralNameP
    }
  }
}
