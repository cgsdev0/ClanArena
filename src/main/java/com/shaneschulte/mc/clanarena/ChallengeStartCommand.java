package com.shaneschulte.mc.clanarena;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ChallengeStartCommand extends Command {
    public ChallengeStartCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (args.length != 1)
        {
            // invalid number of arguments
            return false;
        }

        OfflinePlayer caller = (OfflinePlayer) sender;
        OfflinePlayer target = Bukkit.getPlayer(UUID.fromString(args[0]));

        if (target != null && target.isOnline())
        {
            //target was found and is online
            return false;
        }

        if (caller == target)
        {
            //cant challenge self
            return false;
        }

        ChallengeStartEvent event = new ChallengeStartEvent(caller, target);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled())
        {
            return true;
        }
        return false;
    }
}
