package dev.jsinco.breweryrecipes

import dev.jsinco.breweryrecipes.guis.GuiItemType
import dev.jsinco.breweryrecipes.guis.PaginatedGui
import dev.jsinco.breweryrecipes.guis.RecipeGui
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class Events(private val plugin: BreweryRecipes) : Listener {

    @EventHandler
    fun onGuiClick(event: InventoryClickEvent) {
        if (event.inventory.holder !is RecipeGui) return
        event.isCancelled = true
        val paginatedGUI: PaginatedGui = (event.inventory.holder as RecipeGui).paginatedGui

        val player: Player = event.whoClicked as Player
        val clickedItem: ItemStack = event.currentItem ?: return

        val guiItemType: GuiItemType = GuiItemType.valueOf(clickedItem.itemMeta?.persistentDataContainer?.get(
            NamespacedKey(plugin,"gui-item-type"), PersistentDataType.STRING) ?: return)

        when (guiItemType) {
            GuiItemType.PREVIOUS_PAGE -> {
                val currentPage = paginatedGUI.indexOf(event.inventory)
                if (currentPage == 0) return
                player.openInventory(paginatedGUI.getPage(currentPage - 1))
            }
            GuiItemType.NEXT_PAGE -> {
                val currentPage = paginatedGUI.indexOf(event.inventory)
                if (currentPage == paginatedGUI.size - 1) return
                player.openInventory(paginatedGUI.getPage(currentPage + 1))
            }
            else -> {}
        }
    }
}