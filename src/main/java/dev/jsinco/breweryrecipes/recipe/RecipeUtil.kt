package dev.jsinco.breweryrecipes.recipe

import dev.jsinco.breweryrecipes.BreweryConfig

object RecipeUtil {

    fun getAllRecipes(): List<Recipe> {
        val configurationSection = BreweryConfig.get().getConfigurationSection("recipes") ?: return emptyList()
        val recipes: MutableList<Recipe> = mutableListOf()

        for (recipe in configurationSection.getKeys(false)) {
            val ingredientsRaw = configurationSection.getStringList("$recipe.ingredients")
            val ingredientsMap: MutableMap<String, Int> = mutableMapOf()
            for (ingredientRaw in ingredientsRaw) {
                ingredientsMap[ingredientRaw.substringBefore("/")] = ingredientRaw.substringAfter("/").toInt()
            }

            val rarityWeight = if (configurationSection.contains("$recipe.rarity_weight")) {
                configurationSection.getInt("$recipe.rarity_weight")
            } else {
                configurationSection.getInt("$recipe.difficulty")
            }

            recipes.add(
                Recipe(
                    recipe,
                    configurationSection.getString("$recipe.name") ?: "Unnamed recipe",
                    configurationSection.getInt("$recipe.cookingtime"),
                    rarityWeight,
                    ingredientsMap
                )
            )
        }
        return recipes
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