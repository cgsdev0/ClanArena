package com.shaneschulte.mc.clanarena.adapters;

import org.bukkit.Bukkit;

public class GroupManager {

    private static IGroupAdapter adapter;

    public static IGroupAdapter get(){
        if(adapter == null) {
            if (Bukkit.getServer().getPluginManager().isPluginEnabled("SimpleClans")) {
                adapter = new SimpleClansAdapter();
            }
            else if(Bukkit.getServer().getPluginManager().isPluginEnabled("Factions")) {
                adapter = new FactionsAdapter();
            }
            else {
                adapter = new TeamAdapter();
            }
        }
        return adapter;
    }
}
