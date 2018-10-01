package com.shaneschulte.mc.clanarena.protocols;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.GameMode;
import org.bukkit.plugin.Plugin;

public class RespawnHandler extends PacketAdapter {

    public RespawnHandler(Plugin plugin) {
        super(plugin, PacketType.Play.Server.UPDATE_HEALTH);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        float health = event.getPacket().getFloat().read(0);
        if (Math.signum(health) != 1) {
            event.getPacket().getFloat().write(0, 20f);
            event.getPlayer().setHealth(20f);
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
}

