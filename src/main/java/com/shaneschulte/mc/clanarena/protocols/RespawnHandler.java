package com.shaneschulte.mc.clanarena.protocols;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.shaneschulte.mc.clanarena.Group;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import net.minecraft.server.v1_13_R1.PacketPlayInClientCommand;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class RespawnHandler extends PacketAdapter {

    Group challengers;
    Group opponents;
    Group spectators;
    private HashMap<OfflinePlayer, Location> locations;

    public RespawnHandler(Plugin plugin, ListenerPriority listenerPriority, Group challengers, Group opponents, PacketType... types) {
        super(plugin, listenerPriority, types);
        this.spectators = new Group("Spectators", "Spectators");
        this.challengers = challengers;
        this.opponents = opponents;
        this.locations = new HashMap<>();
        this.challengers.members.forEach(member -> locations.put(member.getPlayer(), member.getPlayer().getLocation()));
        this.opponents.members.forEach(member -> locations.put(member.getPlayer(), member.getPlayer().getLocation()));
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.UPDATE_HEALTH) {
            Player player = event.getPlayer();
            Group group = (challengers.members.contains(player) ? challengers : (opponents.members.contains(player) ? opponents : null));

            if (group == null) {
                return;
            }

            float health = event.getPacket().getFloat().read(0);
            if (Math.signum(health) != 1) {
                player.spigot().respawn();
                group.members.remove(player);
                spectators.addMember(player);

                Optional<OfflinePlayer> participant = group.members.stream().filter(member -> member.getPlayer().getGameMode() != GameMode.SPECTATOR).findFirst();
                if (participant.isPresent()) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.setSpectatorTarget(participant.get().getPlayer());

                } else {
                    spectators.members.addAll(challengers.members);
                    spectators.members.addAll(opponents.members);

                    BukkitRunnable runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            OfflinePlayer spectator = spectators.members.get(0);

                            spectator.getPlayer().setGameMode(GameMode.SURVIVAL);
                            spectator.getPlayer().teleport(locations.get(spectator));

                            spectators.members.remove(spectator);
                            if (spectators.members.size() == 0) {
                                this.cancel();
                                ProtocolLibrary.getProtocolManager().removePacketListener(RespawnHandler.this);
                            }
                        }
                    };

                    runnable.runTaskTimer(getPlugin(), 1, spectators.members.size());
                }
            }
        }
    }
}
