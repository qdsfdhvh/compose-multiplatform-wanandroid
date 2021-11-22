package me.seiko.jetpack.navigation2

class NavGraph internal constructor(
  val initialRoute: String,
  val scenes: List<Scene>
)

class NavGraphBuilder(
  private val initialRoute: String,
  private val scenes: MutableList<Scene> = mutableListOf()
) {

  fun scene(scene: Scene) {
    scenes += scene
  }

  fun build() = NavGraph(
    initialRoute = initialRoute,
    scenes = scenes
  )
}
