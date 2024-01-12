package dev.jsinco.breweryrecipes.breweryeffects.effects

import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.Util
import dev.jsinco.breweryrecipes.breweryeffects.BreweryEffect
import dev.jsinco.breweryrecipes.breweryeffects.EventType
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

class FatedOmen : BreweryEffect {

    override fun effect(plugin: BreweryRecipes, ticks: Long, player: Player) {
        val dataContainer = player.persistentDataContainer

        if (!dataContainer.has(NamespacedKey(plugin, "fated_omen"), PersistentDataType.INTEGER)) {
            dataContainer.set(NamespacedKey(plugin, "fated_omen"), PersistentDataType.INTEGER, 0)
            player.sendMessage(Util.colorcode("&8You feel extremely sick after drinking..."))
        } else {
            player.health = 0.0
            player.sendMessage(Util.colorcode("&8You were reclaimed by the &4omen&8..."))
        }
    }

    override fun executeEffect(plugin: BreweryRecipes, player: Player, eventType: EventType, event: Any?) {
        val timesTakenDamage = player.persistentDataContainer.get(NamespacedKey(plugin, "fated_omen"), PersistentDataType.INTEGER) ?: return
        if (timesTakenDamage >= 3) {
            player.persistentDataContainer.remove(NamespacedKey(plugin, "fated_omen"))
            player.health = 0.0
            player.sendMessage(Util.colorcode("&8You were reclaimed by the &4omen&8..."))
        } else {
            player.persistentDataContainer.set(NamespacedKey(plugin, "fated_omen"), PersistentDataType.INTEGER, timesTakenDamage + 1)
            player.sendMessage(Util.colorcode("&4${timesTakenDamage+1}&8..."))
        }
    }

    override fun listenFor(): List<EventType> {
        return listOf(EventType.ON_DAMAGED)
    }
}