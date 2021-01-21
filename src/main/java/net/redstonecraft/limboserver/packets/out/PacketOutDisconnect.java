package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketOutDisconnect implements PacketOut {

    private final String json;

    public PacketOutDisconnect(String message) {
        this.json = "{\"text\":\"" + message.replaceAll("\"", "\\\"") + "\"}";
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeString(this.json);
    }
}
