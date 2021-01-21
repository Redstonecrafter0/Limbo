package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketStatusOutResponse implements PacketOut {

    private final String response;

    public PacketStatusOutResponse(String response) {
        this.response = response;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeString(response);
    }
}
