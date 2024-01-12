package dev.jsinco.breweryrecipes.breweryeffects.effects

import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffect
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffects
import dev.jsinco.breweryrecipes.breweryeffects.EventType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import java.util.UUID

class LunarLullaby : BreweryEffect {

    companion object {
        private val currentlyEffected: MutableSet<UUID> = mutableSetOf()
    }

    override fun effect(plugin: BreweryRecipes, ticks: Long, player: Player) {
        BreweryEffects.tempAddToSet(player.uniqueId, currentlyEffected, ticks)
    }

    override fun executeEffect(plugin: BreweryRecipes, player: Player, eventType: EventType, event: Any?) {
        if (!currentlyEffected.contains(player.uniqueId) || event !is EntityTargetLivingEntityEvent) return
        event.isCancelled = true
    }

    override fun listenFor(): List<EventType> {
        return listOf(EventType.ON_ENTITY_TARGET_ENTITY)
    }
}