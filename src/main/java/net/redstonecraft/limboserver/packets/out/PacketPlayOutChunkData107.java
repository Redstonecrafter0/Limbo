package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.world.Chunk;

import java.io.IOException;

public class PacketPlayOutChunkData107 implements PacketOut {

    private final Chunk chunk;

    public PacketPlayOutChunkData107(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeInt(chunk.getX());
        packetSerializer.writeInt(chunk.getZ());
        packetSerializer.writeBoolean(true);
        packetSerializer.writeVarInt(25565);
        packetSerializer.writeByteArray(chunk.getMapWithPalette());
    }
}
