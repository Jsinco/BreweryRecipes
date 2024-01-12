package dev.jsinco.breweryrecipes.breweryeffects.effects

import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffect
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffects
import dev.jsinco.breweryrecipes.breweryeffects.EventType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

class GlowingGoblet : BreweryEffect {

    companion object {
        private val currentlyGlowing: MutableSet<UUID> = mutableSetOf()
    }

    override fun effect(plugin: BreweryRecipes, ticks: Long, player: Player) {
        BreweryEffects.tempAddToSet(player.uniqueId, currentlyGlowing, ticks)
        player.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, ticks.toInt(), 0, true, true, true))
        player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, ticks.toInt(), 0, true, true, true))
    }

    override fun executeEffect(plugin: BreweryRecipes, player: Player, eventType: EventType, event: Any?) {
        if (!currentlyGlowing.contains(player.uniqueId)) return
        player.getNearbyEntities(10.0, 10.0, 10.0).forEach {
            if (it is LivingEntity) {
                it.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 220, 0, true, true, true))
                it.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 220, 0, true, true, true))
            }
        }
    }

    override fun listenFor(): List<EventType> {
        return listOf(EventType.RUNNABLE)
    }

}