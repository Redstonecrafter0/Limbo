package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public interface PacketOut {

    void write(PacketSerializer packetSerializer) throws IOException;

}
