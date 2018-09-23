package com.shaneschulte.mc.clanarena;

import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of players.
 */
public class Group {

    public final List<OfflinePlayer> members;
    public final String name;
    public final String tag;

    public Group(String name, String tag) {
        this.name = name;
        this.tag = tag;
        this.members = new ArrayList<>();
    }

    public void addMember(OfflinePlayer member) {
        members.add(member);
    }
}
