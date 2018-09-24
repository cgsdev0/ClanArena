package com.shaneschulte.mc.clanarena.listeners;

import com.shaneschulte.mc.clanarena.events.OnChallengeStart;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ClanArenaListener implements Listener {

    private JavaPlugin plugin;
    private BukkitRunnable runnable;
    private OnChallengeStart startEvent;

    public ClanArenaListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onChallenge(OnChallengeStart event) {

        startEvent = event;

        for (OfflinePlayer player : event.getOpponents().members) {
            MsgUtils.sendMessage((CommandSender) player, "You are being challenged by " + event.getOpponents().name);
            MsgUtils.sendMessage((CommandSender) player, "Type [...] to opt-out");
        }

        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                //move players to arena
            }
        };

        runnable.runTaskLater(plugin, 20 * 3);
    }

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent event)
    {
        if (startEvent != null) {
            startEvent.getChallengers().members.remove(event.getEntity());
            startEvent.getOpponents().members.remove(event.getEntity());

            if (startEvent.getChallengers().members.size() == 0 && startEvent.getOpponents().members.size() == 0) {
                startEvent = null;
                runnable = null;
            }
        }
    }
}
