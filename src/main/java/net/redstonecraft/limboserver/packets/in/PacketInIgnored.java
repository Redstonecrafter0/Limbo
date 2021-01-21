package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;

public class PacketInIgnored implements PacketIn {

    @Override
    public void readPacket(PacketSerializer packetSerializer) {
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
    }
}
