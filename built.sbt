name := "Sniffy Server and Core Tests"

version := "1.0-SNAPSHOT"

description := "Sniffy Server and Core Tests"

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false



// http://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12"

libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % "test"

testOptions += Tests.Argument(TestFrameworks.JUnit)