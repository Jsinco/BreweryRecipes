package dev.jsinco.breweryrecipes.breweryeffects

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent
import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.breweryeffects.effects.*
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import java.util.*

class BreweryEffects : TabExecutor, Listener {

    companion object {
        fun tempAddToSet(uuid: UUID, set: MutableSet<UUID>, ticks: Long) {
            set.add(uuid)
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
                set.remove(uuid)
            }, ticks)
        }

        private val plugin: BreweryRecipes = BreweryRecipes.getInstance()
        val allowMovementEvent: MutableSet<UUID> = mutableSetOf()

        private val effects: Map<String, BreweryEffect> = mapOf(
            "fated_omen" to FatedOmen(),
            "glowing_goblet" to GlowingGoblet(),
            "ender_ale" to EnderAle(),
            "intellect_infusion" to IntellectInfusion(),
            "lunar_lullaby" to LunarLullaby(),
            "floral_concoction" to FloralConcoction()
        )
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 3) return false

        val effect: BreweryEffect = effects[args[0].lowercase()] ?: return false
        val ticks = args[1].replace("none", "0").replace("null", "0").toLongOrNull() ?: return false
        effect.effect(plugin, ticks, Bukkit.getPlayerExact(args[2]) ?: return false)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> effects.keys.toMutableList()
            2 -> mutableListOf("20", "100", "200", "1200", "6000", "12000", "24000", "48000", "96000", "none", "null")
            else -> null
        }
    }


    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            for (player: Player in Bukkit.getOnlinePlayers()) {
                for (effect: BreweryEffect in effects.values) {
                    fireEffect(player, null, EventType.RUNNABLE)
                }
            }
        }, 0, 70L)
    }

    @EventHandler fun onPlayerDamaged(event: EntityDamageEvent) {
        fireEffect(event.entity as? Player ?: return, event, EventType.ON_DAMAGED)
    }

    @EventHandler fun onPlayerInteract(event: PlayerInteractEvent) {
        fireEffect(event.player, event, EventType.ON_INTERACT)
    }

    @EventHandler fun onExpGainEvent(event: PlayerPickupExperienceEvent) {
        fireEffect(event.player, event, EventType.ON_PICKUP_EXP)
    }

    @EventHandler fun onEntityTargetEntity(event: EntityTargetLivingEntityEvent) {
        fireEffect(event.target as? Player ?: return, event, EventType.ON_ENTITY_TARGET_ENTITY)
    }

    @EventHandler fun onPlayerMove(event: PlayerMoveEvent) {
        if (!allowMovementEvent.contains(event.player.uniqueId) || !event.hasChangedBlock()) return // Prevent spam
        fireEffect(event.player, event, EventType.ON_PLAYER_MOVE)
    }


    private fun fireEffect(player: Player, event: Any?, eventType: EventType) {
        for (effect: BreweryEffect in effects.values) {
            if (effect.listenFor().contains(eventType)) {
                effect.executeEffect(plugin, player, eventType, event)
            }
        }
    }
}