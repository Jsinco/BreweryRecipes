package dev.jsinco.breweryrecipes.breweryeffects.effects

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent
import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffect
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffects
import dev.jsinco.breweryrecipes.breweryeffects.EventType
import org.bukkit.entity.Player
import java.util.UUID

class IntellectInfusion : BreweryEffect {

    companion object {
        private val currentlyEffected: MutableSet<UUID> = mutableSetOf()
    }

    override fun effect(plugin: BreweryRecipes, ticks: Long, player: Player) {
        BreweryEffects.tempAddToSet(player.uniqueId, currentlyEffected, ticks)
    }

    override fun executeEffect(plugin: BreweryRecipes, player: Player, eventType: EventType, event: Any?) {
        if (event !is PlayerPickupExperienceEvent) return
        event.experienceOrb.experience = (event.experienceOrb.experience * 1.25).toInt()
    }

    override fun listenFor(): List<EventType> {
        return listOf(EventType.ON_PICKUP_EXP)
    }
}