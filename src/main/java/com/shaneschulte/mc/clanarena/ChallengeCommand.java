package com.shaneschulte.mc.clanarena;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ChallengeCommand extends Command {
    public ChallengeCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (args.length != 2)
        {
            // invalid number of arguments
            return false;
        }

        OfflinePlayer caller = Bukkit.getPlayer(UUID.fromString(args[0]));
        OfflinePlayer target = Bukkit.getPlayer(UUID.fromString(args[1]));

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

        ChallengeEvent event = new ChallengeEvent(caller, target);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled())
        {
            return true;
        }
        return false;
    }
}
