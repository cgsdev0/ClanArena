package com.shaneschulte.mc.clanarena.commands.clanarena.kit;

import com.shaneschulte.mc.clanarena.inventory.KitManager;
import com.shaneschulte.mc.clanarena.command.BaseCommand;
import com.shaneschulte.mc.clanarena.command.LeafCommand;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddCommand extends LeafCommand {

    public AddCommand(BaseCommand parent) {
        super(parent,"add", "clanarena.kit.add", 2, "Create a new kit");
    }

    @Override
    public String getUsage() {
        return "[kit name] [kit icon]";
    }

    @Override
    protected Iterable<String> getSuggestions(int argument) {
        if(argument == 0) return null;
        return Stream.of(Material.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean perform(Player p, List<String> args) {
        try {
            Material mat = Material.valueOf(args.get(1).toUpperCase());
            KitManager.createKit(p, args.get(0), mat);
            MsgUtils.sendMessage(p, "New kit created successfully!");
        }
        catch(IllegalArgumentException e) {
            MsgUtils.error(p, e.getMessage());
        }
        return true;
    }
}
