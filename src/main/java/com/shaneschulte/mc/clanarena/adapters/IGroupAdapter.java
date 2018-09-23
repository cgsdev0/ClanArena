package com.shaneschulte.mc.clanarena.adapters;

import com.shaneschulte.mc.clanarena.Group;
import org.bukkit.OfflinePlayer;

/**
 * Abstraction for interfacing with 'group' plugins,
 * such as SimpleClans and Factions.
 */
public interface IGroupAdapter {

    /**
     * Retrieve a 'group' of players.
     * @param tag The group tag to search for
     * @return Matching group, returns null if not found
     */
    public Group getByTag(String tag);

    /**
     * Retrieve a 'group' of players.
     * @param player The player to find the group of
     * @return Matching group, returns null if not found
     */
    public Group getByPlayer(OfflinePlayer player);

}
