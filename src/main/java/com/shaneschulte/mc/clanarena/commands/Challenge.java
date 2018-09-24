package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.entity.Player;

public class Challenge implements CmdProperties {

    @Override
    public void perform(final Player p, final String allArgs, final String[] args) {
        //What to do when the command is done
        String clan = args[1];
        MsgUtils.sendMessage(p, "You want to challenge " + MsgUtils.Colors.HIGHLIGHT + clan);
    }

    @Override
    public String getCommand() {
        return "Challenge";
    }

    @Override
    public int getLength() {
        return 1; //How many extra args, so one is like /command this (one)
    }

    @Override
    public String getHelpMessage() {
        return "Challenge a rival clan!";
    }

    @Override
    public String getPermission() {
        return "clanarena.challenge";
    }

    @Override
    public boolean isAlias() {
        return false; //Isn't a alias
    }

    @Override
    public CmdProperties getAlias() {
        return null; //Normal command, no alias
    }

    @Override
    public String getUsage() {
        return "/ca challenge <clan>";
    }
}