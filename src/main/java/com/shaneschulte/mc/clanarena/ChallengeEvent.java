package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.adapters.GroupManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class ChallengeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private Group[] groups;

    public ChallengeEvent(OfflinePlayer challenger, OfflinePlayer opponent) {
        this.isCancelled = false;
        this.groups = new Group[2];
        this.groups[0] = GroupManager.get().getByPlayer(challenger);
        this.groups[1] = GroupManager.get().getByPlayer(opponent);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    public List<OfflinePlayer> getChallengers() {
        return groups[0].members;
    }

    public List<OfflinePlayer> getOpponents() {
        return groups[1].members;
    }
}
