# Ingredients Tuplecutter

[ ![Download](https://api.bintray.com/packages/buildo/maven/ingredients-tuplecutter/images/download.svg) ](https://bintray.com/buildo/maven/ingredients-tuplecutter/_latestVersion)

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
Add the buildo/maven Bintray resolver and the dependency to your `build.sbt`

```scala
resolvers += "bintray buildo/maven" at "http://dl.bintray.com/buildo/maven"

libraryDependencies += "io.buildo" %% "ingredients-tuplecutter" % "..."
```
