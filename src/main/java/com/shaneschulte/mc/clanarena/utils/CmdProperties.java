package com.shaneschulte.mc.clanarena.utils;

import org.bukkit.entity.Player;

public interface CmdProperties {

    void perform(Player p, String allArgs, String[] args);

    String getCommand();

    int getLength();

    String getUsage();

    String getHelpMessage();

    String getPermission();

    boolean isAlias();

    CmdProperties getAlias();

}