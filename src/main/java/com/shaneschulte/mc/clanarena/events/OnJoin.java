package com.shaneschulte.mc.clanarena.events;

import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    /**
     * login message, add dynamic content later
     */
    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        MsgUtils.sendMessage(event.getPlayer(), "Welcome! Message board:");
        MsgUtils.raw(event.getPlayer(), "-= " + MsgUtils.Colors.HIGHLIGHT + "clan1" + MsgUtils.Colors.INFO + " claimed victory over " + MsgUtils.Colors.HIGHLIGHT + "clan2" + MsgUtils.Colors.INFO + " =-");
        MsgUtils.raw(event.getPlayer(), "-= there are no other messages     =-");
    }
}
