package com.shaneschulte.mc.clanarena;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class ClanArenaListener implements Listener {

    @EventHandler
    public void onChallenge (ChallengeEvent event) {
        MsgUtils.broadcastMessage("Challengers: " + event.getChallengers().toString());
        MsgUtils.broadcastMessage("Opponents: " + event.getChallengers().toString());
    }
}
