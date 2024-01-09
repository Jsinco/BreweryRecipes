package dev.jsinco.breweryrecipes.guis

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class RecipeGui(val player: Player) : InventoryHolder {

    val inv: Inventory = Bukkit.createInventory(this, )
    init {

    }





    override fun getInventory(): Inventory {
        TODO("Not yet implemented")
    }



    companion object : Listener {

    }
}