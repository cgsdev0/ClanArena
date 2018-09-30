package com.shaneschulte.mc.clanarena.listeners;

import com.shaneschulte.mc.clanarena.events.OnChallengeStart;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ClanArenaListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onChallenge(OnChallengeStart event) {

        for (Player player : event.getOpponents().getOnlineMembers()) {
            MsgUtils.sendMessage(player, "You are being challenged by " + event.getOpponents().getName());
            MsgUtils.sendMessage(player, "Type [...] to opt-out");
        }

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                //
            }
        };
    }
}
