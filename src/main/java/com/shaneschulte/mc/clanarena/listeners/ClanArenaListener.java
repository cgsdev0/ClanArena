package com.shaneschulte.mc.clanarena.listeners;

import com.shaneschulte.mc.clanarena.events.OnChallengeStart;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ClanArenaListener implements Listener {

    private JavaPlugin plugin;

    public ClanArenaListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onChallenge(OnChallengeStart event) {

        for (OfflinePlayer player : event.getOpponents().members) {
            MsgUtils.sendMessage((CommandSender) player, "You are being challenged by " + event.getOpponents().name);
            MsgUtils.sendMessage((CommandSender) player, "Type [...] to opt-out");
        }

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                //
            }
        };
    }
}
