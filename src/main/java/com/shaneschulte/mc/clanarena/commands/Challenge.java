package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.Group;
import com.shaneschulte.mc.clanarena.adapters.GroupManager;
import com.shaneschulte.mc.clanarena.events.OnChallengeStart;
import com.shaneschulte.mc.clanarena.utils.AutoCompletable;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class Challenge implements CmdProperties, AutoCompletable {

    public Challenge() {
    }

    @Override
    public void perform(Player player, String allArgs, String[] args) {

        Group challengers = GroupManager.get().getByPlayer(player);
        Group opponents = GroupManager.get().getByTag(args[1]);
        if (challengers == null || opponents == null)
        {
            MsgUtils.sendMessage(player, "Group not found");
            return;
        }

        if (challengers.name.equals(opponents.name))
        {
            MsgUtils.sendMessage(player, "You can't challenge yourself");
            return;
        }

        int groupSize = Integer.min(Integer.min(Integer.parseInt(args[2]), challengers.members.size()), opponents.members.size());
        if (groupSize <= 0) {
            MsgUtils.sendMessage(player, "Too few players");
            return;
        }
        else {
            challengers.members.removeIf(member -> challengers.members.size() > groupSize);
            opponents.members.removeIf(member -> opponents.members.size() > groupSize);
        }

        OnChallengeStart event = new OnChallengeStart(challengers, opponents);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public String getCommand() {
        return "challenge";
    }

    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public String getUsage() {
        return "/ca Challenge <group> <group size>";
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
    public List<ArrayList<String>> getAutocompleteOptions(/*CommandSender sender*/) {
        // Array for all autocomplete lists (there may only be one but it still has to be in an array list itself as well)
        List<ArrayList<String>> allOptions = new ArrayList<>();

        // for args 01
        ArrayList<String> clanOptions = new ArrayList<>();
        clanOptions.addAll(GroupManager.get().listGroupTags());

        // for args 02
        ArrayList<String> sizeOptions = new ArrayList<>();
        sizeOptions.add(String.valueOf(GroupManager.get().getByTag(clanOptions.get(0)).members.size()));

        allOptions.add(clanOptions);
        allOptions.add(sizeOptions);

        return allOptions;
    }
}
