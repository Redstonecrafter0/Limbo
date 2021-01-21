package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.Main;
import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayOutRespawn47 implements PacketOut {

    private final int dimension;

    public PacketPlayOutRespawn47(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeInt(dimension);
        packetSerializer.writeByte((byte) 0);
        packetSerializer.writeByte(Main.getConfig().getGameMode());
        packetSerializer.writeString("default");
    }
}
