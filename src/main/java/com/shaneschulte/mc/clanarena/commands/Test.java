package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.entity.Player;

public class Test implements CmdProperties {
    @Override
    public void perform(Player p, String allArgs, String[] args) {
        MsgUtils.sendMessage(p, "hi! ");
    }

    @Override
    public String getCommand() {
        return "test";
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public String getUsage() {
        return "/ca test";
    }

    @Override
    public String getHelpMessage() {
        return "for testing code";
    }

    @Override
    public String getPermission() {
        return "clanarena.test";
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
