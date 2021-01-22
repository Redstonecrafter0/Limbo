package net.redstonecraft.limboserver.packets.protocols;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.in.*;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutRespawn47;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutTabListHeaderFooter47;

public class Protocol335 extends Protocol110 {

    public Protocol335(NetworkManager networkManager) {
        super(networkManager);
        registerPacketOut(Status.PLAY, 0x47, null);
        registerPacketOut(Status.PLAY, 0x49, PacketPlayOutTabListHeaderFooter47.class);
        registerPacketOut(Status.PLAY, 0x33, null);
        registerPacketOut(Status.PLAY, 0x34, PacketPlayOutRespawn47.class);
        registerPacketIn(Status.PLAY, 0x0B, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x1F, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x0C, PacketPlayInKeepAlive47.class);
        registerPacketIn(Status.PLAY, 0x0E, PacketPlayInMove47.class);
        registerPacketIn(Status.PLAY, 0x02, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x03, PacketPlayInChatMessage47.class);
    }

    @Override
    public int[] getVersions() {
        return new int[]{335};
    }

}
