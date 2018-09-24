package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossbarCmd implements CmdProperties {
    @Override
    public void perform(Player p, String allArgs, String[] args) {
        BossBar bar = Bukkit.getServer().createBossBar("Your Clan", BarColor.BLUE, BarStyle.SEGMENTED_12);
        bar.addPlayer(p);
    }

    @Override
    public String getCommand() {
        return "bossbar";
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public String getUsage() {
        return "/bossbar";
    }

    @Override
    public String getHelpMessage() {
        return "/bossbar";
    }

    @Override
    public String getPermission() {
        return "clanarena.bossbar";
    }

    @Override
    public boolean isAlias() {
        return false;
    }

    @Override
    public CmdProperties getAlias() {
        return null;
    }
}
