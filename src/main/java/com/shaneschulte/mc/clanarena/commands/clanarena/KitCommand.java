package com.shaneschulte.mc.clanarena.commands.clanarena;

import com.shaneschulte.mc.clanarena.commands.clanarena.kit.*;
import com.shaneschulte.mc.clanarena.command.BaseCommand;
import com.shaneschulte.mc.clanarena.command.BranchCommand;

public class KitCommand extends BranchCommand {

    public KitCommand(BaseCommand parent) {
        super(parent,"kit", "Kit editing commands");
        this.registerSubCommand(new AddCommand(this));
        this.registerSubCommand(new RemoveCommand(this));
    }
}
