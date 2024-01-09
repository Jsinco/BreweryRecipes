package dev.jsinco.breweryrecipes

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object BreweryConfig {
    private val plugin: BreweryRecipes = BreweryRecipes.getInstance()
    private val filePath: String = "${plugin.dataFolder.getParent()}${File.separator}Brewery${File.separator}config.yml"

    fun configExists(): Boolean {
        return File(filePath).exists()
    }

    fun get(): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(File(filePath))
    }
}