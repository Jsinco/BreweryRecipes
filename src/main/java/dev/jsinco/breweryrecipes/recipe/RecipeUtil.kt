package dev.jsinco.breweryrecipes.recipe

import dev.jsinco.breweryrecipes.BreweryConfig

class RecipeUtil {

    fun getAllRecipes(): List<Recipe> {
        val configurationSection = BreweryConfig.get().getConfigurationSection("recipes") ?: return emptyList()
        val recipes: MutableList<Recipe> = mutableListOf()

        for (recipe in configurationSection.getKeys(false)) {
            val ingredientsRaw = configurationSection.getStringList("$recipe.ingredients")
            val ingredientsMap: MutableMap<String, Int> = mutableMapOf()
            for (ingredientRaw in ingredientsRaw) {
                ingredientsMap[ingredientRaw.substringBefore("/")] = ingredientRaw.substringAfter("/").toInt()
            }

            recipes.add(
                Recipe(configurationSection.getString("$recipe.name") ?: "Unnamed recipe",
                configurationSection.getInt("$recipe.cookingtime"),
                configurationSection.getInt("$recipe.difficulty"),
                ingredientsMap)
            )
        }
        return recipes
    }
}