package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.Group;
import com.shaneschulte.mc.clanarena.adapters.GroupManager;
import com.shaneschulte.mc.clanarena.events.OnChallengeStart;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Random;

public class Challenge implements CmdProperties {

    public Challenge() {
    }

    @Override
    public void perform(Player player, String allArgs, String[] args) {
        if (args.length != 2)
        {
            MsgUtils.sendMessage(player, "invalid number of arguments");
            return;
        }

        Group challengers = GroupManager.get().getByTag(player.getName());
        Group opponents = GroupManager.get().getByTag(args[0]);
        int groupSize = Integer.min(Integer.min(Integer.parseInt(args[1]), challengers.members.size()), opponents.members.size());

        if (challengers == null || opponents == null)
        {
            MsgUtils.sendMessage(player, "Group not found");
            return;
        }

        if (challengers.name == opponents.name)
        {
            MsgUtils.sendMessage(player, "You can't challenge yourself");
        }

        if (groupSize <= 0) {
            MsgUtils.sendMessage(player, "Too few players");
            return;
        }
        else {
            challengers.members.removeIf(member -> challengers.members.size() > groupSize);
            opponents.members.removeIf(member -> opponents.members.size() > groupSize);
        }

        OnChallengeStart event = new OnChallengeStart(challengers, opponents);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public String getCommand() {
        return "Challenge";
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public String getUsage() {
        return "/ca Challenge <player>";
    }

    @Override
    public String getHelpMessage() {
        return "Challenge a rival clan!";
    }

    @Override
    public boolean isAlias() {
        return false;
    }

    @Override
    public CmdProperties getAlias() {
        return null;
    }

    @Override
    public String getPermission() {
        return "clanarena.challenge";
    }
}
