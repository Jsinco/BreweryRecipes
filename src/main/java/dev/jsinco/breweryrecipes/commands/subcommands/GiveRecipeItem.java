package dev.jsinco.breweryrecipes.commands.subcommands;

import dev.jsinco.breweryrecipes.BreweryRecipes;
import dev.jsinco.breweryrecipes.Util;
import dev.jsinco.breweryrecipes.commands.SubCommand;
import dev.jsinco.breweryrecipes.recipe.Recipe;
import dev.jsinco.breweryrecipes.recipe.RecipeItem;
import dev.jsinco.breweryrecipes.recipe.RecipeUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GiveRecipeItem implements SubCommand {
    @Override
    public void execute(@NotNull BreweryRecipes plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command (Give yourself a specific recipe item)");
            return;
        }


        Recipe recipe = RecipeUtil.getRecipeFromKey(args[1]);
        ItemStack recipeItem = new RecipeItem(recipe).getItem();

        Util.giveItem(player, recipeItem);
    }

    @Nullable
    @Override
    public List<String> tabComplete(@NotNull BreweryRecipes plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        return RecipeUtil.getAllRecipeKeys();
    }
}
