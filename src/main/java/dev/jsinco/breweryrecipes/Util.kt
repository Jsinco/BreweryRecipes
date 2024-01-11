package dev.jsinco.breweryrecipes

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object Util {

    var prefix: String = colorcode(BreweryRecipes.getInstance().config.getString("prefix") ?: "")
    val plugin: BreweryRecipes = BreweryRecipes.getInstance()

    fun reloadPrefix() {
        prefix = colorcode(plugin.config.getString("prefix") ?: "")
    }

    private const val WITH_DELIMITER = "((?<=%1\$s)|(?=%1\$s))"

    /**
     * @param text The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    @JvmStatic
    fun colorcode(text: String): String {
        val texts = text.split(String.format(WITH_DELIMITER, "&").toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val finalText = StringBuilder()
        var i = 0
        while (i < texts.size) {
            if (texts[i].equals("&", ignoreCase = true)) {
                //get the next string
                i++
                if (texts[i][0] == '#') {
                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)).toString() + texts[i].substring(7))
                } else {
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]))
                }
            } else {
                finalText.append(texts[i])
            }
            i++
        }
        return finalText.toString()
    }


    @JvmStatic
    fun colorArrayList(list: List<String>): List<String> {
        val coloredList: MutableList<String> = ArrayList()
        for (string in list) {
            coloredList.add(colorcode(string))
        }
        return coloredList
    }

    @JvmStatic
    fun giveItem(player: Player, item: ItemStack) {
        for (i in 0..35) {
            if (player.inventory.getItem(i) == null || player.inventory.getItem(i)!!.isSimilar(item)) {
                player.inventory.addItem(item)
                break
            } else if (i == 35) {
                player.world.dropItem(player.location, item)
            }
        }
    }

    @JvmStatic
    fun itemNameFromMaterial(item: String): String {
        var name = item.lowercase().replace("_", " ")
        name = name.substring(0, 1).uppercase() + name.substring(1)
        for (i in name.indices) {
            if (name[i] == ' ') {
                name =
                    name.substring(0, i) + " " + name[i + 1].toString().uppercase() + name.substring(
                        i + 2
                    ) // Capitalize first letter of each word
            }
        }
        return name
    }


    @JvmStatic
    fun getRecipeBookItem(): ItemStack {
        val item = ItemStack(Material.valueOf(plugin.config.getString("recipe_book_item.material") ?: "BOOK"))
        val meta = item.itemMeta!!
        meta.setDisplayName(colorcode(plugin.config.getString("recipe_book_item.name") ?: "&6&lRecipe Book"))
        meta.lore = colorArrayList(plugin.config.getStringList("recipe_book_item.lore"))
        if (plugin.config.getBoolean("recipe_book_item.glint")) {
            meta.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 1, true)
        }
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS)
        meta.persistentDataContainer.set(NamespacedKey(plugin, "recipe-book"), PersistentDataType.BOOLEAN, true)
        item.itemMeta = meta
        return item
    }
}