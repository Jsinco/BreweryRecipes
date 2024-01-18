package dev.jsinco.breweryrecipes.breweryeffects.effects

import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffect
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffects
import dev.jsinco.breweryrecipes.breweryeffects.EventType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.EquipmentSlot
import java.util.UUID

class FloralConcoction : BreweryEffect {

    companion object {
        private val currentlyEffected: MutableSet<UUID> = mutableSetOf()
        private val plantableMaterials: List<Material> = listOf(
            Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.PODZOL, Material.FARMLAND, Material.MUD
        )
        private val flowers: List<Material> = listOf(
            Material.ORANGE_TULIP, Material.PINK_TULIP, Material.RED_TULIP, Material.WHITE_TULIP, Material.AZURE_BLUET,
            Material.ALLIUM, Material.BLUE_ORCHID, Material.CORNFLOWER, Material.DANDELION, Material.OXEYE_DAISY, Material.POPPY
        )

        fun noPlace(player: Player): Boolean {
            val event = BlockPlaceEvent(
                player.location.block,
                player.location.block.state,
                player.location.block,
                player.inventory.itemInMainHand,
                player,
                true,
                EquipmentSlot.HAND
            )
            Bukkit.getPluginManager().callEvent(event)
            return event.isCancelled
        }
    }

    override fun effect(plugin: BreweryRecipes, ticks: Long, player: Player) {
        BreweryEffects.tempAddToSet(player.uniqueId, BreweryEffects.allowMovementEvent, ticks)
        BreweryEffects.tempAddToSet(player.uniqueId, currentlyEffected, ticks)
    }

    override fun executeEffect(plugin: BreweryRecipes, player: Player, eventType: EventType, event: Any?) {
        if (!currentlyEffected.contains(player.uniqueId) || noPlace(player)) return

        if (plantableMaterials.contains(player.location.add(0.0,-1.0,0.0).block.type)) {
            player.location.block.type = flowers.random()
            player.spawnParticle(Particle.VILLAGER_HAPPY, player.location, 6, 0.5, 0.5, 0.5, 0.0)
        }
    }

    override fun listenFor(): List<EventType> {
        return listOf(EventType.ON_PLAYER_MOVE)
    }
}