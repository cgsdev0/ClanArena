package com.shaneschulte.mc.clanarena.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MsgUtils {

    /**
     * ClanArena prefix
     */
    private static String prefix = "&bClanArena&e> &r";

    /**
     * Colors variable ex. Colors.HIGHLIGHT to highlight some text
     */
    public enum Colors {
        INFO(ChatColor.GRAY),
        WARNING(ChatColor.GOLD),
        ERROR(ChatColor.RED),
        SUCCESS(ChatColor.GREEN),
        VARIABLE(ChatColor.YELLOW),
        HIGHLIGHT(ChatColor.AQUA),
        ;

        private final ChatColor chatColor;

        Colors (final ChatColor colorCode) {
            this.chatColor = colorCode;
        }

        @Override
        public String toString() {
            return (chatColor.toString());
        }
    }

    /**
     * Sends a message to a single target
     * @param target the target you want to send the message to
     * @param message the message being sent
     */
    static public void sendMessage (CommandSender target, String message) {
        target.sendMessage(colorMessage(prefix + Colors.INFO + message));
    }

    static public void error (CommandSender target, String message) {
        target.sendMessage(colorMessage(prefix + Colors.ERROR + message));
    }

    static public void success (CommandSender target, String message) {
        target.sendMessage(colorMessage(prefix + Colors.SUCCESS + message));
    }

    /**
     * Sends a message to all players
     * @param message the message being sent to all players
     */
    static public void broadcastMessage (String message) {
        Bukkit.getServer().broadcastMessage(colorMessage(prefix + Colors.INFO + message));
    }

    /**
     * Sends a message to a single target without a ClanArena prefix
     * @param target the target you want to send the message to
     * @param message the message being sent
     */
    static public void raw (CommandSender target, String message) {
        target.sendMessage(colorMessage(Colors.INFO + message));
    }

    /**
     * Logs a message to the console
     * @param message the message to log to console
     */
    public static void log(String message) {
        Bukkit.getLogger().info(colorMessage(prefix + message));
    }

    /**
     * Colors a message with color codes ('&')
     * @param message the message to be colored
     * @return the colored message
     */
    static private String colorMessage(String message) {
        char colorCode = '&';
        return ChatColor.translateAlternateColorCodes(colorCode, message);
    }
}