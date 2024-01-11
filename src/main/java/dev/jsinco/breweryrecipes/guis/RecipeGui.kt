package dev.jsinco.breweryrecipes.guis

import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.recipe.Recipe
import dev.jsinco.breweryrecipes.recipe.RecipeUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class RecipeGui(player: Player) : InventoryHolder {

    companion object : Listener {
        private val plugin: BreweryRecipes = BreweryRecipes.getInstance()
    }

    private val inv: Inventory = Bukkit.createInventory(this, plugin.config.getInt("gui.size"), plugin.config.getString("gui.title") ?: "Recipes")
    private val recipeGuiItems: MutableList<ItemStack> = mutableListOf()

    init {
        val borderItems: List<Pair<List<Int>, ItemStack>> = GuiItem.getAllGuiBorderItems()
        for (guiItem in borderItems) {
            for (slot in guiItem.first) {
                inv.setItem(slot, guiItem.second)
            }
        }
        val arrowItems = GuiItem.getPageArrowItems()
        for (slot in arrowItems.first.first) {
            inv.setItem(slot, arrowItems.first.second)
        }
        for (slot in arrowItems.second.first) {
            inv.setItem(slot, arrowItems.second.second)
        }

        val recipes: List<Recipe> = RecipeUtil.getAllRecipes()
        var totalRecipes = 0

        for (recipe in recipes) {
            if (!player.hasPermission("breweryrecipes.recipe.${recipe.recipeKey}")) continue
            recipeGuiItems.add(GuiItem.createRecipeGuiItem(recipe))
            totalRecipes++
        }

        val totalRecipesItem = GuiItem.getTotalRecipesItem(totalRecipes, recipes.size)
        for (slot in totalRecipesItem.first) {
            inv.setItem(slot, totalRecipesItem.second)
        }
    }

    val paginatedGui: PaginatedGui = PaginatedGui(plugin.config.getString("gui.title") ?: "Recipes", inv, recipeGuiItems)


    init {

    }
    fun openRecipeGui(viewer: Player) {
        viewer.openInventory(paginatedGui.getPage(0))
    }



    override fun getInventory(): Inventory {
        return inv
    }

}