package com.shaneschulte.mc.clanarena.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.shaneschulte.mc.clanarena.ClanArena;
import com.shaneschulte.mc.clanarena.Group;
import com.shaneschulte.mc.clanarena.adapters.GroupManager;
import com.shaneschulte.mc.clanarena.protocols.ChallengeHandler;
import com.shaneschulte.mc.clanarena.protocols.RespawnHandler;
import com.shaneschulte.mc.clanarena.utils.AutoCompletable;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class ChallengeCmd implements CmdProperties, AutoCompletable, Listener {

    @Override
    public void perform(Player player, String allArgs, String[] args) {

        Group challengers = GroupManager.get().getByPlayer(player);
        Group opponents = GroupManager.get().getByTag(args[1]);

        if (challengers == null) {
            MsgUtils.sendMessage(player, "You are not in a group");
            return;
        }
        if (opponents == null) {
            MsgUtils.sendMessage(player, "Group [" + args[1] + "] not found");
            return;
        }

        if (challengers.name.equals(opponents.name)) {
            MsgUtils.sendMessage(player, "You can't challenge yourself");
            return;
        }

        challengers.members.removeIf(member -> !member.isOnline());
        opponents.members.removeIf(member -> !member.isOnline());

        challengers.members.forEach(member -> {
            MsgUtils.sendMessage((CommandSender) member, "Challenging " + opponents.name);
            member.getPlayer().setGameMode(GameMode.ADVENTURE);
        });
        opponents.members.forEach(member -> {
            MsgUtils.sendMessage((CommandSender) member, "You are being challenged by " + challengers.name);
            MsgUtils.sendMessage((CommandSender) member, "Type [No] to opt-out");
            member.getPlayer().setGameMode(GameMode.ADVENTURE);
        });

        ChallengeHandler challengeHandler = new ChallengeHandler(ClanArena.getPlugin(), ListenerPriority.NORMAL, challengers, opponents, PacketType.Play.Client.CHAT);
        //delay for opt-outs
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                ProtocolLibrary.getProtocolManager().removePacketListener(challengeHandler);

                int groupSize = Integer.min(Integer.min(Integer.parseInt(args[2]), challengers.members.size()), opponents.members.size());
                if (groupSize <= 0) {
                    MsgUtils.sendMessage(player, "Too few players");
                    return;
                }

                RespawnHandler handler = new RespawnHandler(ClanArena.getPlugin(), ListenerPriority.NORMAL, challengers, opponents, PacketType.Play.Server.UPDATE_HEALTH);
                ProtocolLibrary.getProtocolManager().addPacketListener(handler);
            }
        };
        ProtocolLibrary.getProtocolManager().addPacketListener(challengeHandler);

        runnable.runTaskLater(ClanArena.getPlugin(), 100);
    }

    @Override
    public String getCommand() {
        return "challenge";
    }

    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public String getUsage() {
        return "/ca Challenge <group> <group size>";
    }

    @Override
    public String getHelpMessage() {
        return "Challenge a rival clan!";
    }

    @Override
    public String getPermission() {
        return "clanarena.challenge";
    }

    @Override
    public List<ArrayList<String>> getAutocompleteOptions(/*CommandSender sender*/) {
        // Array for all autocomplete lists (there may only be one but it still has to be in an array list itself as well)
        List<ArrayList<String>> allOptions = new ArrayList<>();

        // for args 01
        ArrayList<String> clanOptions = new ArrayList<>();
        clanOptions.addAll(GroupManager.get().listGroupTags());

        // for args 02
        ArrayList<String> sizeOptions = new ArrayList<>();
        sizeOptions.add(String.valueOf(GroupManager.get().getByTag(clanOptions.get(0)).members.size()));

        allOptions.add(clanOptions);
        allOptions.add(sizeOptions);

        return allOptions;
    }
}
