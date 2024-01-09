package dev.jsinco.breweryrecipes

import org.bukkit.Difficulty

data class Recipe (
    val name: String,
    val cookingTime: Int,
    val difficulty: Int,
    val ingredients: Map<String, Int>
)