

name := "AWSBot"

version := "1.0"

scalaVersion := "2.9.2"

resolvers ++= Seq(
  Resolver.url("Vocalocity Respository", url("http://69.61.97.221:81/repository"))(Patterns ("[organisation]/jars/[module]-[revision].[ext]")).mavenStyle(),
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.3.26"

libraryDependencies += "HippyJava"     % "HippyJava"    % "1.0.0-SNAPSHOT"

libraryDependencies += "com.novocode" % "junit-interface" % "0.10-M2" % "test"

libraryDependencies += "commons-codec" % "commons-codec" % "1.5"
