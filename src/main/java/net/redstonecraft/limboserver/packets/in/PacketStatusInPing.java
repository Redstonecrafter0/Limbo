package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.packets.out.PacketStatusOutPong;

import java.io.IOException;

public class PacketStatusInPing implements PacketIn {

    private long id;

    @Override
    public void readPacket(PacketSerializer packetSerializer) throws IOException {
        id = packetSerializer.readLong();
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
        networkManager.sendPacket(playerConnection, new PacketStatusOutPong(id));
    }
}
