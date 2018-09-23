package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClanArenaListener implements Listener {

    @EventHandler
    public void onChallenge (ChallengeStartEvent event) {
        if (event.isCancelled()) {
            return;
        }

        MsgUtils.broadcastMessage("Challengers: " + event.getChallengers().toString());
        MsgUtils.broadcastMessage("Opponents: " + event.getChallengers().toString());
    }
}
