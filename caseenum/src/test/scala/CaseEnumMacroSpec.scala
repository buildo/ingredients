import ingredients.caseenum.annotations.{enum, indexedEnum}

import org.scalatest.{ Matchers, WordSpec }

class CaseEnumMacroSpec extends WordSpec with Matchers {
  @enum trait Planet {
    Mercury
    Venus
    Earth
  }

  @enum trait Beer {
    object Lager
    object Ale
  }

  "@enum annotation" should {
    "produce a valid CaseEnum-style ADT" in {
      Planet.Earth shouldBe a[Product]
      Planet.Earth shouldBe a[Serializable]
      Planet.Earth shouldBe a[Planet]
      Planet.Mercury should not be a [Planet.Earth.type]
      Planet.Earth shouldBe Planet.Earth
    }

    "produce a valid CaseEnum-style ADT (alternative syntax)" in {
      Beer.Lager shouldBe a[Product]
      Beer.Lager shouldBe a[Serializable]
      Beer.Lager shouldBe a[Beer]
      Beer.Ale should not be a [Beer.Lager.type]
      Beer.Lager shouldBe Beer.Lager
    }
  }

}

class IndexedCaseEnumMacroSpec extends WordSpec with Matchers {
  @indexedEnum trait Planet {
    type Index = Int
    Mercury { 1 }
    Venus   { 2 }
    Earth   { 3 }
  }

  @indexedEnum trait Beer {
    type Index = Int
    Lager { 1 }
    Ale { 2 }
  }

  "@indexedEnum annotation" should {
    "produce a valid IndexedCaseEnum-style ADT" in {
      val typecheck: Int = 3: Planet#Index
      Planet.Earth shouldBe a[Product]
      Planet.Earth shouldBe a[Serializable]
      Planet.Earth shouldBe a[Planet]
      Planet.Mercury should not be a [Planet.Earth.type]
      Planet.Earth shouldBe Planet.Earth
      Planet.Earth.index shouldBe 3
    }

    "produce a valid IndexedCaseEnum-style ADT (alternative syntax)" in {
      val typecheck: Int = 2: Planet#Index
      Beer.Lager shouldBe a[Product]
      Beer.Lager shouldBe a[Serializable]
      Beer.Lager shouldBe a[Beer]
      Beer.Ale should not be a [Beer.Lager.type]
      Beer.Lager shouldBe Beer.Lager
      Beer.Ale.index shouldBe 2
    }
  }

}
