package com.shaneschulte.mc.clanarena.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.lucko.commodore.Commodore;
import org.bukkit.command.PluginCommand;

public class CommodoreRegistrar {
    public static void registerCompletions(Commodore commodore, PluginCommand command) {
        // TODO: use commodore
        /*commodore.register(command, LiteralArgumentBuilder.literal("mycommand")
                .then(RequiredArgumentBuilder.argument("some-argument", StringArgumentType.string()))
                .then(RequiredArgumentBuilder.argument("some-other-argument", BoolArgumentType.bool()))
        );*/
    }
}
