package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketStatusOutPong implements PacketOut {

    private final long id;

    public PacketStatusOutPong(long id) {
        this.id = id;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeLong(id);
    }
}
