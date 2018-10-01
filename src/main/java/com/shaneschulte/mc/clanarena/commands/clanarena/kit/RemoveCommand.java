package com.shaneschulte.mc.clanarena.commands.clanarena.kit;

import com.shaneschulte.mc.clanarena.command.BaseCommand;
import com.shaneschulte.mc.clanarena.command.LeafCommand;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveCommand extends LeafCommand {

    public RemoveCommand(BaseCommand parent) {
        super(parent,"remove", "clanarena.kit.remove", 1, "Removes an existing kit");
    }

    @Override
    public String getUsage() {
        return "[kit name]";
    }

    @Override
    protected Iterable<String> getSuggestions(int argument) {
        return null;
    }

    @Override
    public boolean perform(Player p, List<String> args) {
            MsgUtils.sendMessage(p, "Placeholder");
        return true;
    }
}
