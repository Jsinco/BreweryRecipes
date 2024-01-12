package dev.jsinco.breweryrecipes.recipe

import dev.jsinco.breweryrecipes.BreweryConfig
import org.bukkit.configuration.ConfigurationSection
import kotlin.random.Random

object RecipeUtil {

    fun getAllRecipes(): List<Recipe> {
        val recipes: MutableList<Recipe> = mutableListOf()
        val configurationSection = BreweryConfig.get().getConfigurationSection("recipes") ?: return emptyList()
        for (recipe in configurationSection.getKeys(false)) {
            recipes.add(getRecipeFromKey(recipe))
        }
        return recipes
    }

    @JvmStatic
    fun getAllRecipeKeys(): List<String> {
        val configurationSection = BreweryConfig.get().getConfigurationSection("recipes") ?: return emptyList()
        return configurationSection.getKeys(false).toList()
    }

    @JvmStatic
    fun getRecipeFromKey(recipe: String): Recipe {
        val configurationSection: ConfigurationSection = BreweryConfig.get().getConfigurationSection("recipes")!!
        val ingredientsRaw = configurationSection.getStringList("$recipe.ingredients")
        val ingredientsMap: MutableMap<String, Int> = mutableMapOf()
        for (ingredientRaw in ingredientsRaw) {
            ingredientsMap[ingredientRaw.substringBefore("/")] = ingredientRaw.substringAfter("/").toInt()
        }

        return Recipe(
            recipe,
            configurationSection.getString("$recipe.name") ?: "Unnamed Recipe",
            configurationSection.getInt("$recipe.difficulty"),
            configurationSection.getInt("$recipe.cookingtime"),
            configurationSection.getInt("$recipe.distillruns"),
            if (configurationSection.getInt("$recipe.distilltime") == 0) configurationSection.getInt("$recipe.distilltime") else 40,
            configurationSection.getInt("$recipe.age"),
            ingredientsMap,
            if (configurationSection.contains("$recipe.rarity_weight")) configurationSection.getInt("$recipe.rarity_weight")
            else configurationSection.getInt("$recipe.difficulty")
        )
    }


    fun getRandomRecipe(): Recipe {
        val recipes = getAllRecipes()
        var selectedRecipe = recipes.random()
        while (Random.nextInt(11) < selectedRecipe.rarityWeight) {
            selectedRecipe = recipes.random()
        }
        return selectedRecipe
    }



    // We need the one in the middle!
    // recipeName/recipeName2/recipeName3
    // recipeName/recipeName2

    fun parseRecipeName(recipeName: String): String {
        if (!recipeName.contains("/")) {
             return recipeName
        }
        val newString = recipeName.substringAfter("/")
        if (newString.contains("/")) {
            return newString.substring(0, newString.indexOf("/"))
        }
        return newString
    }

    fun parseIngredientsName(string: String): String {
        if (string.contains(":")) {
            return string.substringAfter(":")
        }
        return string
    }
}