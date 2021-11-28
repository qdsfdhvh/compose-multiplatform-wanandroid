package me.seiko.kmp.storage

object StorageProviderImpl : StorageProvider {

  private const val rootDir = "compose-chat"

  override val appDir: String
    get() = "${getWorkDirectory()}/$rootDir/app".mkdirs()

  override val cacheDir: String
    get() = "${getWorkDirectory()}/$rootDir/cache".mkdirs()

  override val mediaCacheDir: String
    get() = "${getWorkDirectory()}/$rootDir/mediaCaches".mkdirs()

  private fun getWorkDirectory(): String {
    var workingDirectory: String?
    // here, we assign the name of the OS, according to Java, to a variable...
    val os = (System.getProperty("os.name"))?.uppercase() ?: throw Error("Can't get os name for this device!")
    // to determine what the workingDirectory is.
    // if it is some version of Windows
    if (os.contains("WIN")) {
      // it is simply the location of the "AppData" folder
      workingDirectory = System.getenv("AppData")
    }
    // Otherwise, we assume Linux or Mac
    else {
      // in either case, we would start in the user's home directory
      workingDirectory = System.getProperty("user.home")
      // if we are on a Mac, we are not done, we look for "Application Support"
      if (os.contains("MAC"))
        workingDirectory += "/Library/Application Support"
    }
    return workingDirectory ?: throw Error("Can't get work directory for os:$os!")
  }
}
