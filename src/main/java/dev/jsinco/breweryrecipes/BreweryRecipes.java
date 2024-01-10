package dev.jsinco.breweryrecipes;

import dev.jsinco.breweryrecipes.guis.RecipeGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// Idea:
// Allow recipes for brews to be collected from randomly generated chests and make some recipes rarer than others
// Has a gui that shows all the recipes the player has collected and how to make them
// Pulls directly from the Brewery plugin's config.yml file

public final class BreweryRecipes extends JavaPlugin implements CommandExecutor {

    private static BreweryRecipes instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        instance = this;

        getServer().getPluginManager().registerEvents(new Events(this), this);
        getCommand("breweryrecipes").setExecutor(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BreweryRecipes getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        assert sender instanceof Player;
        new RecipeGui((Player) sender);
        return true;
    }
}
