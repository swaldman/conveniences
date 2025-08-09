import mill._
import mill.scalalib._
import mill.scalalib.publish._

object conveniences extends RootModule with ScalaModule with PublishModule {

  override def scalaVersion = "3.3.6"

  def scalacOptions = T {
    super.scalacOptions() :+ "-deprecation" //++ Seq("-explain")
  }

  override def artifactName = "conveniences"
  override def publishVersion = T{"0.0.6-SNAPSHOT"}
  override def pomSettings    = T{
    PomSettings(
      description = "Conveniences, usually extension methods, that seem useful across projects.",
      organization = "com.mchange",
      url = "https://github.com/swaldman/conveniences",
      licenses = Seq(License.`Apache-2.0`),
      versionControl = VersionControl.github("swaldman", "conveniences"),
      developers = Seq(
	Developer("swaldman", "Steve Waldman", "https://github.com/swaldman")
      )
    )
  }
}


