package com.shaneschulte.mc.clanarena.adapters;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.events.DisbandClanEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SimpleClansGroup implements Group {

    private Clan clan;

    SimpleClansGroup(Clan clan) {
        this.clan = clan;
    }

    @Override
    public String getTag() {
        return clan.getTag();
    }

    @Override
    public String getName() {
        return clan.getName();
    }

    @Override
    public boolean isMember(Player p) {
        return clan.isMember(p);
    }

    @Override
    public List<Player> getOnlineMembers() {
        if(clan == null) return null;
        List<Player> result = new ArrayList<>();
        clan.getOnlineMembers().forEach(player -> result.add(player.toPlayer()));
        return result;
    }

    @Override
    public List<OfflinePlayer> getAllMembers() {
        if(clan == null) return null;
        List<OfflinePlayer> result = new ArrayList<>();
        clan.getAllMembers().forEach(player -> result.add(Bukkit.getOfflinePlayer(player.getUniqueId())));
        return result;
    }
}
