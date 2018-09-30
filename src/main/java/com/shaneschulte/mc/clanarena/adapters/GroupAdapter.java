package com.shaneschulte.mc.clanarena.adapters;

import org.bukkit.OfflinePlayer;

import java.util.List;

/**
 * Abstraction for interfacing with 'group' plugins,
 * such as SimpleClans and Factions.
 */
public interface GroupAdapter {

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

    /**
     * Retrieves a list of all the group tags.
     * @return List of strings (group tags)
     */
    public List<Group> listGroups();
}
