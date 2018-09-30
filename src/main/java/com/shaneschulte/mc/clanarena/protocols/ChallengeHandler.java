package com.shaneschulte.mc.clanarena.protocols;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.shaneschulte.mc.clanarena.Group;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.stream.Stream;

public class ChallengeHandler extends PacketAdapter {
    Group challengers;
    Group opponents;

    public ChallengeHandler(Plugin plugin, ListenerPriority listenerPriority, Group challengers, Group opponents, PacketType... types) {
        super(plugin, listenerPriority, types);
        this.challengers = challengers;
        this.opponents = opponents;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CHAT) {
            Player player = event.getPlayer();
            Group group = (challengers.members.contains(player) ? challengers : (opponents.members.contains(player) ? opponents : null));
            if (group == null) {
                return;
            }

            if (event.getPacket().getStrings().read(0).equalsIgnoreCase("no")) {
                group.members.remove(player);
                MsgUtils.sendMessage(player, "You have opted-out");
                Stream stream = Stream.concat(challengers.members.stream(), opponents.members.stream());
                stream.forEach(member -> MsgUtils.sendMessage((CommandSender) member, player.getName() + " has opted-out"));
                event.setCancelled(true);

            }
        }
    }
}
