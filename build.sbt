name := "CustomTransformer"

version := "0.1"

scalaVersion := "2.12.14"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.4"

// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.4"

//libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % "3.2.9" % "test"

// https://mvnrepository.com/artifact/com.github.jai-imageio/jai-imageio-core
libraryDependencies += "com.github.jai-imageio" % "jai-imageio-core" % "1.4.0"

