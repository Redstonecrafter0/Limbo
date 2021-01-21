package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public interface PacketIn {

    void readPacket(PacketSerializer packetSerializer) throws IOException;

    void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection);

}
