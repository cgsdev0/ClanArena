package com.shaneschulte.mc.clanarena.adapters;

import com.shaneschulte.mc.clanarena.Group;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class SimpleClansAdapter implements IGroupAdapter {

    ClanManager clans;

    public SimpleClansAdapter() {
        this.clans = SimpleClans.getInstance().getClanManager();
    }

    private Group makeGroup(Clan clan) {
        if(clan == null) return null;

        Group result = new Group(clan.getName(), clan.getTag());
        clan.getAllMembers().forEach(player ->
                result.addMember(Bukkit.getOfflinePlayer(player.getUniqueId())));

        return result;
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
    public List<String> listGroupTags() {
        List<String> results = new ArrayList<>();
        clans.getClans().forEach(clan -> results.add(clan.getTag()));
        return results;
    }
}
