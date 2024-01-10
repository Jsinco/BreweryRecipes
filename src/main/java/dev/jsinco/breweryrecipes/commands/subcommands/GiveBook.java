package dev.jsinco.breweryrecipes.commands.subcommands;

import dev.jsinco.breweryrecipes.BreweryRecipes;
import dev.jsinco.breweryrecipes.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GiveBook implements SubCommand {
    @Override
    public void execute(@NotNull BreweryRecipes plugin, @NotNull CommandSender sender, @NotNull String[] args) {

    }

    @Nullable
    @Override
    public List<String> tabComplete(@NotNull BreweryRecipes plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
