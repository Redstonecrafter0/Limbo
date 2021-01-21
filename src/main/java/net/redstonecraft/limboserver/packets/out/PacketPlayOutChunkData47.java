package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.world.Chunk;

import java.io.IOException;

public class PacketPlayOutChunkData47 implements PacketOut {

    private final Chunk chunk;

    public PacketPlayOutChunkData47(Chunk chunk) {
        this.chunk = chunk;
    }

    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeInt(chunk.getX());
        packetSerializer.writeInt(chunk.getZ());
        packetSerializer.writeBoolean(true);
        packetSerializer.writeShort((short) 65535);
        packetSerializer.writeByteArray(chunk.getMap());
    }
}

