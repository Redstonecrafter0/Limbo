package net.redstonecraft.limboserver.packets.protocols;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.in.PacketPlayInKeepAlive340;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutKeepAlive340;

public class Protocol340 extends Protocol338 {

    public Protocol340(NetworkManager networkManager) {
        super(networkManager);
        registerPacketIn(Status.PLAY, 0x0B, PacketPlayInKeepAlive340.class);
        registerPacketOut(Status.PLAY, 0x1F, PacketPlayOutKeepAlive340.class);
    }

    @Override
    public int[] getVersions() {
        return new int[]{340};
    }

    @Override
    public void sendKeepAlive(PlayerConnection playerConnection, int id) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutKeepAlive340(id));
    }

}
