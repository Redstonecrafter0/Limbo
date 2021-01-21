package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayInKeepAlive47 implements PacketIn {

    private int id;

    @Override
    public void readPacket(PacketSerializer packetSerializer) throws IOException {
        id = packetSerializer.readVarInt();
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
        if (id == networkManager.getCurrentKeepAliveId()) {
            playerConnection.keepAlive();
        }
    }
}
