package dev.jsinco.breweryrecipes.breweryeffects.effects

import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffect
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffects
import dev.jsinco.breweryrecipes.breweryeffects.EventType
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID

class FloralConcoction : BreweryEffect {

    companion object {
        private val currentlyEffected: MutableSet<UUID> = mutableSetOf()
        private val plantableMaterials: List<Material> = listOf(
            Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.PODZOL, Material.FARMLAND,

        )
    }

    override fun effect(plugin: BreweryRecipes, ticks: Long, player: Player) {
        BreweryEffects.tempAddToSet(player.uniqueId, BreweryEffects.allowMovementEvent, ticks)
        BreweryEffects.tempAddToSet(player.uniqueId, currentlyEffected, ticks)
    }

    override fun executeEffect(plugin: BreweryRecipes, player: Player, eventType: EventType, event: Any?) {
        TODO("Not yet implemented")
    }

    override fun listenFor(): List<EventType> {
        TODO("Not yet implemented")
    }
}