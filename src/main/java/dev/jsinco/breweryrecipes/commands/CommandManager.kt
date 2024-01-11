package dev.jsinco.breweryrecipes.commands

import dev.jsinco.breweryrecipes.BreweryConfig
import dev.jsinco.breweryrecipes.BreweryRecipes
import dev.jsinco.breweryrecipes.Util
import dev.jsinco.breweryrecipes.commands.subcommands.GiveBook
import dev.jsinco.breweryrecipes.commands.subcommands.GiveRecipeItem
import dev.jsinco.breweryrecipes.commands.subcommands.GuiCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CommandManager(val plugin: BreweryRecipes) : CommandExecutor, TabCompleter {

    private val commands: Map<String, SubCommand> = mapOf(
        "give" to GiveRecipeItem(),
        "givebook" to GiveBook(),
        "gui" to GuiCommand()
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            plugin.reloadConfig()
            Util.reloadPrefix()
            BreweryConfig.reload()
            sender.sendMessage("${Util.prefix} Reloaded config.")
            return true
        }

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