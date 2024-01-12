package dev.jsinco.breweryrecipes.breweryeffects

import dev.jsinco.breweryrecipes.BreweryRecipes
import org.bukkit.entity.Player

interface BreweryEffect {
    fun effect(plugin: BreweryRecipes, ticks: Long, player: Player)

    fun executeEffect(plugin: BreweryRecipes, player: Player, eventType: EventType, event: Any?)

    fun listenFor(): List<EventType>
}