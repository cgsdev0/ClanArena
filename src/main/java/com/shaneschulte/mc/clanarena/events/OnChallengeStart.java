package com.shaneschulte.mc.clanarena.events;

import com.shaneschulte.mc.clanarena.adapters.Group;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnChallengeStart extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private Group[] groups;

    public OnChallengeStart(Group challengers, Group opponents) {
        this.isCancelled = false;
        this.groups = new Group[]{challengers, opponents};
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
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

    public Group getChallengers() {
        return groups[0];
    }

    public Group getOpponents() {
        return groups[1];
    }
}
