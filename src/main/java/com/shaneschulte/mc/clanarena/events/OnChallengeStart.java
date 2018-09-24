package com.shaneschulte.mc.clanarena.events;

import com.shaneschulte.mc.clanarena.Group;
import com.shaneschulte.mc.clanarena.adapters.GroupManager;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class OnChallengeStart extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private Group[] groups;

    public OnChallengeStart(OfflinePlayer challenger, OfflinePlayer opponent) {
        this.isCancelled = false;
        this.groups = new Group[2];
        this.groups[0] = GroupManager.get().getByPlayer(challenger);
        this.groups[1] = GroupManager.get().getByPlayer(opponent);

        if (this.groups[0] == null || this.groups[1] == null) {
            isCancelled = true;
        }
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
