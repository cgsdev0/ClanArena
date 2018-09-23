package com.shaneschulte.mc.clanarena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

    // ClanArena prefix
    private static String prefix = "&bClanArena&e> &r";

    // Colors config
    public interface Colors {
        String
            info = "&7",
            warning = "&c";
    }

    /**
     * Sends a message to a single target
     * @param target the target you want to send the message to
     * @param message the message being sent
     */
    public void sendMessage (CommandSender target, String message) {
        target.sendMessage(colorMessage(prefix + Colors.info + message));
    }

    /**
     * Sends a message to all players
     * @param message the message being sent to all players
     */
    public void broadcastMessage (String message) {
        Bukkit.getServer().broadcastMessage(colorMessage(prefix + Colors.info + message));
    }

    /**
     * Sends a message to a single target without a ClanArena prefix
     * @param target the target you want to send the message to
     * @param message the message being sent
     */
    public void sendRawMessage (CommandSender target, String message) {
        target.sendMessage(Colors.info + colorMessage(message));
    }

    /**
     * Logs a message to the console
     * @param message the message to log to console
     */
    public void logMessage (String message) {
        Bukkit.getLogger().info(prefix + message);
    }

    /**
     * Colors a message with color codes ('&')
     * @param message the message to be colored
     * @return the colored message
     */
    private String colorMessage(String message) {
        char colorCode = '&';
        return ChatColor.translateAlternateColorCodes(colorCode, message);
    }
}
