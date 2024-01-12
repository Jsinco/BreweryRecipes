package dev.jsinco.breweryrecipes.breweryeffects.effects

import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffect
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffects
import dev.jsinco.breweryrecipes.breweryeffects.EventType
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import java.util.UUID

class EnderAle : BreweryEffect {

    companion object {
        private val currentlyEffected: MutableSet<UUID> = mutableSetOf()
        private val teleportCooldown: MutableSet<UUID> = mutableSetOf()
    }

    override fun effect(plugin: BreweryRecipes, ticks: Long, player: Player) {
        BreweryEffects.tempAddToSet(player.uniqueId, currentlyEffected, ticks)
    }

    override fun executeEffect(plugin: BreweryRecipes, player: Player, eventType: EventType, event: Any?) {
        if (!currentlyEffected.contains(player.uniqueId) || teleportCooldown.contains(player.uniqueId) || event !is PlayerInteractEvent) return

        val loc = player.getTargetBlockExact(40)?.location?.add(0.0,1.0,0.0) ?: return
        player.teleport(loc)
        player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f)
        BreweryEffects.tempAddToSet(player.uniqueId, teleportCooldown, 200L)
    }

    override fun listenFor(): List<EventType> {
        return listOf(EventType.ON_INTERACT)
    }
}