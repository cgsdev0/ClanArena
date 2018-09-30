package com.shaneschulte.mc.clanarena.adapters;

import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class FactionsAdapter implements GroupAdapter {

    // TODO: Implement FactionsAdapter

    @Override
    public Group getByTag(String tag) {
        return null;
    }

    @Override
    public Group getByPlayer(OfflinePlayer player) {
        return null;
    }

    @Override
    public List<Group> listGroups() {
        return new ArrayList<>();
    }
}
