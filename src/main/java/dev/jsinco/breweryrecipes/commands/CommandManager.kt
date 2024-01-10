package dev.jsinco.breweryrecipes.commands


import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.commands.subcommands.GiveRecipeItem
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class CommandManager(val plugin: BreweryRecipes) : CommandExecutor, TabCompleter {

    val commands: MutableMap<String, SubCommand> = mutableMapOf()

    init {
        commands["give"] = GiveRecipeItem()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        val subCommand = commands[args[0]] ?: return false
        subCommand.execute(plugin, sender, args)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): List<String>? {
        if (args.size == 1) return commands.keys.toList()

        val subCommand = commands[args[0]] ?: return null
        return subCommand.tabComplete(plugin, sender, args)
    }

}