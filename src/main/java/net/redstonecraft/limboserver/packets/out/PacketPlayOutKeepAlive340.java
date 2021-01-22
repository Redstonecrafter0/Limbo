package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayOutKeepAlive340 implements PacketOut {

    private final int id;

    public PacketPlayOutKeepAlive340(int id) {
        this.id = id;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeLong(id);
    }

}
