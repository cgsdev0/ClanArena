package com.shaneschulte.mc.clanarena.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class BaseCommand {

    private String command;
    private String permission;
    private String description;
    private String root;

    BaseCommand(BaseCommand parent, String command, String permission, String description) {
        this.command = command;
        this.permission = permission;
        this.description = description;
        this.root = (parent == null) ? "/" + this.getCommand() : (parent.getRoot() + " " + this.getCommand());
    }

    public String getRoot() {
        return root;
    }

    public abstract int getLength();

    /**
     * The code that executes when this subcommand is called
     * @param p Player that executed the command
     * @param args Array of all arguments
     */
    public abstract boolean perform(Player p, List<String> args);

    /**
     * Returns the command and how it will be typed
     * @return command name
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns the command's description (useful for sendHelp)
     * @return command description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the permission needed to use this subcommand
     * @return clanarena.</command name here>
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Send someone help on how to use this command
     */
    public abstract void sendHelp(CommandSender sender);
}
