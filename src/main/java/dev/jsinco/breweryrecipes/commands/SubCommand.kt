package dev.jsinco.breweryrecipes.commands

import dev.jsinco.breweryrecipes.BreweryRecipes
import org.bukkit.command.CommandSender

interface SubCommand {
    fun execute(plugin: BreweryRecipes, sender: CommandSender, args: Array<out String>)

    fun tabComplete(plugin: BreweryRecipes, sender: CommandSender, args: Array<out String>): List<String>?

}