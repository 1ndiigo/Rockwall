package me.cobble.rockwall.cmds.global

import me.cobble.rockwall.rockwall.Rockwall
import me.cobble.rockwall.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ClearChatCommand(plugin: Rockwall) : CommandExecutor {

    init {
        plugin.getCommand("clearchat")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (sender.hasPermission("rockwall.clearchat")) {
                for (i in (0..100)) {
                    for (player: Player in Bukkit.getOnlinePlayers()) {
                        player.sendMessage("\n")
                    }
                }
            } else {
                sender.sendMessage(Utils.color("&cYou do not have permission to use this command"))
                return false
            }
        }
        return false
    }
}