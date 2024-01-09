package dev.jsinco.breweryrecipes;

import org.bukkit.plugin.java.JavaPlugin;

// Idea:
// Allow recipes for brews to be collected from randomly generated chests and make some recipes rarer than others
// Has a gui that shows all the recipes the player has collected and how to make them
// Pulls directly from the Brewery plugin's config.yml file

public final class BreweryRecipes extends JavaPlugin {

    private static BreweryRecipes instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BreweryRecipes getInstance() {
        return instance;
    }
}
