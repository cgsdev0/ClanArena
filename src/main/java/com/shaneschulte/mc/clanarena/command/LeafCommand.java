package com.shaneschulte.mc.clanarena.command;

import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic interface for every command
 */
public abstract class LeafCommand extends BaseCommand {

    private int length;

    public LeafCommand(BaseCommand parent, String command, String permission, int length, String description) {
        super(parent, command, permission, description);
        this.length = length;
    }

    /**
     * Returns an example of using the command
     * @return usage example
     */
    public abstract String getUsage();

    /**
     * Send someone help on how to use this command
     */
    public void sendHelp(CommandSender sender) {
        MsgUtils.sendMessage(sender, MsgUtils.Colors.ERROR + this.getRoot() + " " + this.getUsage());
    }

    /**
     * Returns the number of args required
     * @return 0-99
     */
    public int getLength() {
        return length;
    }

    /**
     * Override this to implement tab suggestions
     * @param argument Argument number (Guaranteed to be less than length)
     * @return All possible completions (or null to show nothing)
     */
    protected abstract Iterable<String> getSuggestions(int argument);

    List<String> onTabComplete(List<String> arguments) {
        List<String> result = new ArrayList<>();
        if (length == 0 || arguments.size() > length) return result; // Out of range

        Iterable<String> possible;
        String argument = "";
        if (arguments.isEmpty()) possible = getSuggestions(0);
        else {
            possible = getSuggestions(arguments.size() - 1);
            argument = arguments.get(arguments.size() - 1);
        }
        if(possible != null) StringUtil.copyPartialMatches(argument, possible, result);
        return result;
    }
}