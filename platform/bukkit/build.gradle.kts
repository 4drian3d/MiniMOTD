plugins {
  id("minimotd.shadow-platform")
  id("xyz.jpenilla.run-paper")
}

dependencies {
  implementation(projects.minimotdCommon)
  implementation(libs.slf4jJdk14)
  implementation(libs.adventurePlatformBukkit)
  implementation(libs.bstatsBukkit)
  implementation(libs.paperlib)
  compileOnly(libs.paperApi)
}

tasks {
  runServer {
    minecraftVersion(minecraftVersion)
  }
  shadowJar {
    platformRelocation("bukkit", "xyz.jpenilla.minimotd.common")
    commonRelocation("org.slf4j")
    commonRelocation("io.leangen.geantyref")
    commonRelocation("net.kyori")
    commonRelocation("org.bstats")
    commonRelocation("io.papermc.lib")
  }
  processResources {
    val props = mapOf(
      "version" to project.version,
      "website" to Constants.GITHUB_URL,
      "description" to project.description
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
      expand(props)
    }
  }
}

runPaper.folia.registerTask()

modrinth {
  loaders.set(listOf("paper", "folia"))
  gameVersions.addAll(bukkitVersions)
}
