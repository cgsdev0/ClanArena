package com.shaneschulte.mc.clanarena.utils;

import org.bukkit.entity.Player;

/**
 * Basic interface for every command
 */
public interface CmdProperties {

    /**
     * The code that executes when this subcommand is called
     * @param p Player that executed the command
     * @param allArgs String of all arguments (remove later?)
     * @param args Array of all arguments, args[0] being the subcommand
     */
    void perform(Player p, String allArgs, String[] args);

    /**
     * Returns the command and how it will be typed
     * @return command name
     */
    String getCommand();

    /**
     * Returns the number of args required (remove later?)
     * @return 0-99
     */
    int getLength();

    /**
     * Returns an example of using the command
     * @return usage example
     */
    String getUsage();

    /**
     * Returns a brief description
     * @return help message
     */
    String getHelpMessage();

    /**
     * Returns the permission needed to use this subcommand
     * @return clanarena.</command name here>
     */
    String getPermission();

    default boolean isAlias() {
        return false;
    }

    default CmdProperties getAlias() {
        return null;
    }

}