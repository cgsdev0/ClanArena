package com.shaneschulte.mc.clanarena.adapters;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class SimpleClansAdapter implements GroupAdapter {

    ClanManager clans;

    public SimpleClansAdapter() {
        this.clans = SimpleClans.getInstance().getClanManager();
    }

    private Group makeGroup(Clan clan) {
        return clan == null ? null : new SimpleClansGroup(clan);
    }

    @Override
    public Group getByTag(String tag) {
        return makeGroup(clans.getClan(tag));
    }

    @Override
    public Group getByPlayer(OfflinePlayer player) {
        return makeGroup(clans.getClanByPlayerUniqueId(player.getUniqueId()));
    }

    @Override
    public List<Group> listGroups() {
        List<Group> results = new ArrayList<>();
        clans.getClans().forEach(clan -> results.add(makeGroup(clan)));
        return results;
    }
}
