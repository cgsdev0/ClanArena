package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.commands.clanarena.KitCommand;
import com.shaneschulte.mc.clanarena.command.BranchCommand;

public class ClanArenaCommand extends BranchCommand {
    public ClanArenaCommand() {
        super(null,"clanarena", "Base command for Clan Arena");
        this.registerSubCommand(new KitCommand(this));
    }
}
