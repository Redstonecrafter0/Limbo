package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.Main;
import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayInMove47 implements PacketIn {

    private double x;
    private double y;
    private double z;
    private boolean onGround;

    @Override
    public void readPacket(PacketSerializer packetSerializer) throws IOException {
        x = packetSerializer.readDouble();
        y = packetSerializer.readDouble();
        z = packetSerializer.readDouble();
        onGround = packetSerializer.readBoolean();
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
        if (y < 0) {
            playerConnection.getProtocol().sendPosition(playerConnection, Main.getConfig().getSpawnX(), Main.getConfig().getSpawnY(), Main.getConfig().getSpawnZ(), Main.getConfig().getSpawnYaw(), Main.getConfig().getSpawnPitch());
        }
    }

}
