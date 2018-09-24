package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.utils.AutoCompletable;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChallengeCmd implements CmdProperties, AutoCompletable {

    @Override
    public void perform(final Player p, final String allArgs, final String[] args) {
        //What to do when the command is done
        String clan = args[1];
        MsgUtils.sendMessage(p, "You want to challenge " + MsgUtils.Colors.HIGHLIGHT + clan);
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
        return "ChallengeCmd a rival clan!";
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

    @Override
    public List<ArrayList<String>> getAutocompleteOptions() {
        // Array for all autocomplete lists (there may only be one but it still has to be in an array list itself as well)
        List<ArrayList<String>> allOptions = new ArrayList<>();

            // for args 01
            ArrayList<String> clanOptions = new ArrayList<>();
            clanOptions.add("clan1");
            clanOptions.add("clan2");

            // for args 02
            ArrayList<String> options2 = new ArrayList<>();
            options2.add("option2");
            options2.add("option4");

        allOptions.add(clanOptions);
        allOptions.add(options2);

        return allOptions;
    }
}