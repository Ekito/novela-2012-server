import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "novela-2012-server"
    val appVersion      = "1.0-SNAPSHOT"
      
    def customLessEntryPoints(base: File): PathFinder = (
        (base / "app" / "assets" / "stylesheets" / "bootstrap" * "style.less") +++
        (base / "app" / "assets" / "stylesheets" * "*.less")
    )

    val appDependencies = Seq(
      "com.github.julienrf" %% "play-jsmessages" % "1.2.1",
      "org.springframework" % "spring-context" % "3.1.2.RELEASE",
      "org.springframework" % "spring-jms" % "3.1.2.RELEASE",
      "org.apache.activemq" % "activemq-core" % "5.6.0",
      "org.apache.xbean" % "xbean-spring" % "3.11.1",
      "commons-io" % "commons-io" % "2.4"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    	lessEntryPoints <<= baseDirectory(customLessEntryPoints),
        resolvers ++= Seq(
                 "julienrf.github.com" at "http://julienrf.github.com/repo/"
        )
    )

}
