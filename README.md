# Ingredients Tuplecutter
Heads and tails for your tuples

_"It's easy to use"_ -- Daniele Gallingani

```scala
scala> import io.buildo.ingredients.tuplecutter._
import io.buildo.ingredients.tuplecutter._

scala> (1, "B", 3).head
res0: Int = 1

scala> (1, "B", 3).tail
res1: (String, Int) = (B,3)
```

## Install
Add the _buildo_ resolver and the dependency to your `build.sbt`

```scala
resolvers += "buildo" at "https://github.com/buildo/mvn/raw/master/releases"

libraryDependencies += "io.buildo" %% "ingredients-logging" % "0.1"
```
