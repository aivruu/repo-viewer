@file:Suppress("UnstableApiUsage")

rootProject.name = "repo-viewer"

sequenceOf("api", "implementation").forEach {
  val kerbalProject = ":${rootProject.name}-$it"
  include(kerbalProject)
  project(kerbalProject).projectDir = file(it)
}
