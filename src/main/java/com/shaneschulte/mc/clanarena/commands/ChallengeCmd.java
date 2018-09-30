package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.adapters.GroupManager;
import com.shaneschulte.mc.clanarena.utils.AutoCompletable;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChallengeCmd implements CmdProperties, AutoCompletable {

    /**
     * basic clan challenge command with autocomplete clans
     */
    @Override
    public void perform(final Player p, final String allArgs, final String[] args) {
        //What to do when the command is done
        String clan = args[1];
        MsgUtils.sendMessage(p, "You want to challenge " + MsgUtils.Colors.HIGHLIGHT + clan);
    }

    /**
     * creates clan list for the first argument autocomplete
     */
    @Override
    public ArrayList<ArrayList<String>> getAutocompleteOptions() {
        // Array for all autocomplete lists (there may only be one but it still has to be in an array list itself as well)
        ArrayList<ArrayList<String>> allOptions = new ArrayList<>();
        ArrayList<String> groupTags = new ArrayList<>();

        GroupManager.get().listGroups().forEach(group->groupTags.add(group.getTag()));

        allOptions.add(groupTags);

        return allOptions;
    }

    @Override
    public String getCommand() {
        return "challenge";
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