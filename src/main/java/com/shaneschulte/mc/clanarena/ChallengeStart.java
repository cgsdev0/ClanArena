package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class ChallengeStart implements CmdProperties {

    public ChallengeStart() {
    }

    private boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (args.length != 1)
        {
            MsgUtils.sendMessage(sender, "invalid number of arguments");
            return false;
        }

        OfflinePlayer caller = (OfflinePlayer) sender;
        OfflinePlayer target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline())
        {
            MsgUtils.sendMessage(sender, args[0] + " was not found");
            return false;
        }

        if (caller == target)
        {
            MsgUtils.sendMessage(sender, "You can't challenge self");
            return false;
        }

        ChallengeStartEvent event = new ChallengeStartEvent(caller, target);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled())
        {
            MsgUtils.sendMessage(sender, "starting challenge");
            return true;
        }
        return false;
    }

    @Override
    public void perform(Player p, String allArgs, String[] args) {
        execute(p, getCommand(), Arrays.copyOfRange(args, 1, 2));
    }

    @Override
    public String getCommand() {
        return "challenge";
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public String getUsage() {
        return "/clanarena challenge [target]";
    }

    @Override
    public String getHelpMessage() {
        return "ChallengeCmd a rival clan!";
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