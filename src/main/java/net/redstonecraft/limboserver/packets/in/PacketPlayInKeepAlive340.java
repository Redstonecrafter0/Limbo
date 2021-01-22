package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayInKeepAlive340 implements PacketIn {

    private int id;
    private boolean success;

    @Override
    public void readPacket(PacketSerializer packetSerializer) throws IOException {
        try {
            id = (int) packetSerializer.readLong();
            success = true;
        } catch (ClassCastException ignored) {
            success = false;
        }
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
        if (success) {
            if (id == networkManager.getCurrentKeepAliveId()) {
                playerConnection.keepAlive();
            }
        } else {
            networkManager.disconnect(playerConnection, "Bad Packet");
        }
    }

}
