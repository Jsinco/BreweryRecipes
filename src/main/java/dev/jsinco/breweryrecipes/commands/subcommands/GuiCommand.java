package dev.jsinco.breweryrecipes.commands.subcommands;

import dev.jsinco.breweryrecipes.BreweryRecipes;
import dev.jsinco.breweryrecipes.commands.SubCommand;
import dev.jsinco.breweryrecipes.guis.RecipeGui;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuiCommand implements SubCommand {

    @Override
    public void execute(@NotNull BreweryRecipes plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /breweryrecipes gui <player> (Open the recipe GUI of a player)");
            return;
        }
        if (!(sender instanceof Player viewer)) {
            sender.sendMessage("§cOnly players can use this command (Open the recipe GUI of a player)");
            return;
        }

        Player player = Bukkit.getPlayerExact(args[1]);
        if (player == null) {
            sender.sendMessage("§cPlayer not found");
            return;
        }

        RecipeGui recipeGui = new RecipeGui(player);
        recipeGui.openRecipeGui(viewer);
    }

    @Nullable
    @Override
    public List<String> tabComplete(@NotNull BreweryRecipes plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
