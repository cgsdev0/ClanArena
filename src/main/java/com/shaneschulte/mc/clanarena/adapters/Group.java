package com.shaneschulte.mc.clanarena.adapters;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of players.
 */
public interface Group {

    public String getTag();

    public String getName();

    public boolean isMember(Player p);

    public List<Player> getOnlineMembers();

    public List<OfflinePlayer> getAllMembers();

}
