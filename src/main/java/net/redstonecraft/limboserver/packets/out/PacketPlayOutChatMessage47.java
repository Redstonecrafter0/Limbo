package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.util.ChatColor;

import java.io.IOException;

public class PacketPlayOutChatMessage47 implements PacketOut {

    private final String message;

    public PacketPlayOutChatMessage47(String message) {
        this.message = message;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeString(ChatColor.colorToJson(message));
        packetSerializer.writeByte((byte) 0);
    }

}
