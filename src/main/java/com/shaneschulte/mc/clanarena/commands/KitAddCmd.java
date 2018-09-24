package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.inventory.KitManager;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KitAddCmd implements CmdProperties {
    @Override
    public void perform(Player p, String allArgs, String[] args) {
        Material mat = Material.valueOf(args[2]);
        if(mat == null) {
            MsgUtils.error(p, "Invalid material " + args[2]);
            return;
        }
        try {
            KitManager.createKit(p, args[1], mat);
            MsgUtils.sendMessage(p, "New kit created successfully!");
        }
        catch(IllegalArgumentException e) {
            MsgUtils.error(p, e.getMessage());
        }
    }

    @Override
    public String getCommand() {
        return "kitadd";
    }

    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public String getUsage() {
        return "/ca kitadd [name] [icon]";
    }

    @Override
    public String getHelpMessage() {
        return "Create a new kit";
    }

    @Override
    public String getPermission() {
        return "clanarena.kit.create";
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
