package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.UUID;

public class ChallengeStart implements CmdProperties {

    public ChallengeStart() {
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

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
//
//        if (caller == target)
//        {
//            MsgUtils.sendMessage(sender, "You can't challenge self");
//            return false;
//        }

        ChallengeStartEvent event = new ChallengeStartEvent(caller, target);
        Bukkit.getPluginManager().callEvent(event);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                MsgUtils.sendMessage(sender, "starting challenge");
            }
        };

        runnable.runTaskLater(sender.getServer().getPluginManager().getPlugin("ClanArena"), 20 * 3);

        return true;
    }

    @Override
    public void perform(Player p, String allArgs, String[] args) {
        execute(p, getCommand(), Arrays.copyOfRange(args, 1, 2));
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
