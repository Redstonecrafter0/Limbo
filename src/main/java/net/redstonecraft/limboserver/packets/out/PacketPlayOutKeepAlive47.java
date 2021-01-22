package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayOutKeepAlive47 implements PacketOut {

    private final int id;

    public PacketPlayOutKeepAlive47(int id) {
        this.id = id;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeVarInt(id);
    }
}
