package dev.jsinco.breweryrecipes.recipe

import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.Util
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class RecipeItem (recipe: Recipe) {

    companion object {
        private val plugin: BreweryRecipes = BreweryRecipes.getInstance()
    }

    val item = ItemStack(Material.valueOf(plugin.config.getString("recipe_item.material")?.uppercase() ?: "PAPER"))

    init {
        val meta = item.itemMeta!!

        meta.setDisplayName(Util.colorcode("&#F7FFC9${RecipeUtil.parseRecipeName(recipe.name)} &fRecipe"))
        meta.lore = Util.colorArrayList(plugin.config.getStringList("recipe_item.lore"))
        meta.persistentDataContainer.set(NamespacedKey(plugin, "recipe-key"), PersistentDataType.STRING, recipe.recipeKey)
        if (plugin.config.getBoolean("recipe_item.glint")) {
            meta.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 1, true)
        }
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        item.itemMeta = meta
    }



}