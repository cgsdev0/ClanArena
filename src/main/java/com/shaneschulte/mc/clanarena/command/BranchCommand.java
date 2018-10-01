package com.shaneschulte.mc.clanarena.command;

import com.shaneschulte.mc.clanarena.command.BaseCommand;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BranchCommand extends BaseCommand {

    private Map<String, BaseCommand> subCommands;

    public BranchCommand(BaseCommand parent, String command, String description) {
        super(parent, command, "", description);
        this.subCommands = new HashMap<>();
    }

    public void registerSubCommand(BaseCommand command) {
        subCommands.put(command.getCommand(), command);
    }

    public int getLength() {
        return 1;
    }

    public Map<String, BaseCommand> getSubCommands() {
        return subCommands;
    }

    /**
     * Send someone help on how to use this command
     */
    public void sendHelp(CommandSender sender) {

        if(!sender.hasPermission(this.getPermission())) return;

        MsgUtils.raw(sender, "&e-=-=-=- &7Availible &bSub&7commands: &e-=-=-=-");
        for (final Map.Entry<String, BaseCommand> entry : subCommands.entrySet()) {
            if (sender.hasPermission(entry.getValue().getPermission())) {
                MsgUtils.raw(sender, new StringBuilder()
                        .append(entry.getValue().getRoot())
                        .append(" - ")
                        .append(entry.getValue().getDescription())
                        .toString());
            }
        }
    }
    /**
     * The code that executes when this subcommand is called
     * @param p Player that executed the command
     * @param args Array of all arguments
     */
    public boolean perform(Player p, List<String> args) {
        MsgUtils.log(this.getCommand() + args.toString());
        if(!args.isEmpty()) {
            String nextArg = args.remove(0);
            if(subCommands.containsKey(nextArg)) {
                BaseCommand nextCommand = subCommands.get(nextArg);
                if (!p.hasPermission(nextCommand.getPermission())) return false;
                if (args.size() < nextCommand.getLength() || !nextCommand.perform(p, args)) {
                    nextCommand.sendHelp(p);
                }
                return true;
            }
        }
        this.sendHelp(p);
        return true;
    }
}
