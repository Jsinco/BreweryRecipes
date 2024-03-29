package dev.jsinco.breweryrecipes.commands.subcommands;

import dev.jsinco.breweryrecipes.BreweryRecipes;
import dev.jsinco.breweryrecipes.Util;
import dev.jsinco.breweryrecipes.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GiveBook implements SubCommand {
    @Override
    public void execute(@NotNull BreweryRecipes plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /breweryrecipes givebook <player> (Give a player the recipe book)");
            return;
        }

        Player player = Bukkit.getPlayerExact(args[1]);
        if (player != null) {
            Util.giveItem(player, Util.getRecipeBookItem());
        }
    }

    @Nullable
    @Override
    public List<String> tabComplete(@NotNull BreweryRecipes plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
