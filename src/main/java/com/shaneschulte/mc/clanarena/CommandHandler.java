package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.commands.Challenge;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {

    private static HashMap<String, CmdProperties> commandClasses = new HashMap<String, CmdProperties>();

    CommandHandler() {
        // REGISTER BASE COMMANDS HERE
        registerArgument(new Challenge());
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        // If the sender is a PLAYER
        if (sender instanceof Player) {
            final Player p = (Player) sender;

            // If there are arguments and not the main command alone
            if (args.length > 0) {
                boolean cont = false;
                CmdProperties cmdClass = null;

                // Assign all commands to their name in the hashmap
                for (final Map.Entry<String, CmdProperties> entry : commandClasses.entrySet()) {
                    if (!cont) {
                        if (entry.getKey().equalsIgnoreCase(args[0])) {
                            cont = true;
                            cmdClass = entry.getValue();
                        }
                    }
                }

                // check for permission i think? idk whats entirely going on here, review later
                if (cont) {
                    final int argsNeeded = cmdClass.getLength();
                    if (args.length - 1 >= argsNeeded) {
                        if (p.hasPermission(cmdClass.getPermission())) {
                            if (args[argsNeeded] == null) {
                                args[argsNeeded] = "Nothing";
                            }
                            final StringBuilder sb = new StringBuilder();
                            for (int i = argsNeeded; i < args.length; i++) {
                                sb.append(args[i]).append(" ");
                            }

                            final String allArgs = sb.toString().trim();
                            if (cmdClass.isAlias())
                                cmdClass.getAlias().perform(p, allArgs, args);
                            else
                                cmdClass.perform(p, allArgs, args);
                            return true;
                        } else {
                            // D: no permission!
                            MsgUtils.error (sender, "(no permission)");
                            return true;
                        }
                    } else {
                        //Not the right amount of arguments for the command. Maybe put something like cmdClass.getUsage() here to show the player how to use the command
                        MsgUtils.error (sender, cmdClass.getUsage());
                        return true;
                    }
                } else {
                    //The argument doesn't exist.
                    MsgUtils.error (sender, "(command does not exist)");
                    return true;
                }
            } else {
                // Not enough arguments
                MsgUtils.error (sender, "(show help here)");
                MsgUtils.error (sender, "(try /ca challenge <name>)");
                return true;
            }
        } else {
            // Sender isn't a player
            MsgUtils.error (sender, "(not a player)");
            return true;
        }
    }

    private void registerArgument(final CmdProperties baseCmd) {
        commandClasses.put(baseCmd.getCommand(), baseCmd);
    }
}