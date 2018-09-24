package com.shaneschulte.mc.clanarena.adapters;

import com.shaneschulte.mc.clanarena.Group;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class FactionsAdapter implements IGroupAdapter {

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
    public List<String> listGroupTags() {
        return new ArrayList<>();
    }
}
