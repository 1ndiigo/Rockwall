package me.cobble.rockwall.listeners

import me.cobble.rockwall.Rockwall
import me.cobble.rockwall.utils.Utils
import me.cobble.rockwall.utils.global.FormatType
import me.cobble.rockwall.utils.groups.GroupType
import me.cobble.rockwall.utils.groups.GroupUtils
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*

class SendToGroupListener(plugin: Rockwall) : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @EventHandler
    fun onSpeakToGroup(event: AsyncPlayerChatEvent) {
        event.isCancelled = true

        if (GroupUtils.getCurrentSpeakingChat(event.player.uniqueId) != null) {

            val group = GroupUtils.getCurrentSpeakingChat(event.player.uniqueId)

            val prefix = GroupUtils.formatMaker(event.player, group, GroupType.NORMAL, FormatType.PREFIX)
            val prefixSeparator =
                GroupUtils.formatMaker(event.player, group, GroupType.NORMAL, FormatType.PREFIX_SEPARATOR)
            val name = GroupUtils.formatMaker(event.player, group, GroupType.NORMAL, FormatType.NAME)
            val nameSeparator = GroupUtils.formatMaker(event.player, group, GroupType.NORMAL, FormatType.NAME_SEPARATOR)

            for (uuid: UUID in group!!.members) {
                val player = Bukkit.getPlayer(uuid)!!
                if (player.isOnline) {
                    player.spigot().sendMessage(
                        *ComponentBuilder()
                            .append(prefix)
                            .appendLegacy(" ")
                            .append(prefixSeparator)
                            .appendLegacy(" ")
                            .append(name)
                            .appendLegacy(" ")
                            .append(nameSeparator)
                            .appendLegacy(" " + Utils.color(event.message, event.player))
                            .create()
                    )
                }
            }
        }
    }
}