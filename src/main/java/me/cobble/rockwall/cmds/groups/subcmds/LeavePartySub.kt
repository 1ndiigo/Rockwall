package me.cobble.rockwall.cmds.groups.subcmds

import me.cobble.rockwall.config.Messages
import me.cobble.rockwall.utils.RockwallBaseCommand
import me.cobble.rockwall.utils.Utils
import me.cobble.rockwall.utils.parties.PartyManager
import me.cobble.rockwall.utils.parties.PartyUtils
import org.bukkit.entity.Player

class LeavePartySub : RockwallBaseCommand() {
    override val name: String
        get() = "leave"
    override val descriptor: String
        get() = "Allows members (but not owners) to leave the chat"
    override val syntax: String
        get() = "/g leave <group name>"

    override fun run(p: Player, args: Array<String>) {
        if (args.isEmpty()) {
            p.sendMessage(Utils.color("&c$syntax"))
            return
        }

        if (!PartyUtils.validateGroupName(args[0])) {
            p.sendMessage(Messages.getGroupString("errors.invalid"))
            return
        }

        val group = PartyManager.getGroup(args[0])

        if (group == null) {
            p.sendMessage(Messages.getGroupString("errors.404"))
            return
        }

        if (!group.isMember(p.uniqueId)) {
            p.sendMessage(Messages.getGroupString("errors.leave-group-not-in"))
            return
        }

        if (group.owner == p.uniqueId) {
            p.sendMessage(Messages.getGroupString("owner-leave-group"))
            return
        }

        group.members.remove(p.uniqueId)
        group.activeSpeakers.remove(p.uniqueId)
        p.sendMessage(Messages.getGroupString("leave"))

        if (group.members.size == 0) {
            PartyManager.deleteGroup(group)
        }
    }
}