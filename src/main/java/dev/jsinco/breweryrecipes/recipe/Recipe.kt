package dev.jsinco.breweryrecipes.recipe

data class Recipe (
    val recipeKey: String,
    val name: String,
    val cookingTime: Int,
    val rarityWeight: Int,
    val ingredients: Map<String, Int>
)