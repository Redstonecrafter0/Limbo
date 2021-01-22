package net.redstonecraft.limboserver.packets.protocols;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.in.PacketInIgnored;
import net.redstonecraft.limboserver.packets.in.PacketPlayInChatMessage47;
import net.redstonecraft.limboserver.packets.in.PacketPlayInKeepAlive47;
import net.redstonecraft.limboserver.packets.in.PacketPlayInMove47;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutPlayerListItem107;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutPlayerPositionAndLook107;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutRespawn47;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutTabListHeaderFooter47;

public class Protocol338 extends Protocol335 {

    public Protocol338(NetworkManager networkManager) {
        super(networkManager);
        registerPacketOut(Status.PLAY, 0x34, null);
        registerPacketOut(Status.PLAY, 0x35, PacketPlayOutRespawn47.class);
        registerPacketIn(Status.PLAY, 0x0E, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x0D, PacketPlayInMove47.class);
        registerPacketIn(Status.PLAY, 0x0C, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x0B, PacketPlayInKeepAlive47.class);
        registerPacketIn(Status.PLAY, 0x03, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x02, PacketPlayInChatMessage47.class);
        registerPacketOut(Status.PLAY, 0x49, null);
        registerPacketOut(Status.PLAY, 0x4A, PacketPlayOutTabListHeaderFooter47.class);
        registerPacketOut(Status.PLAY, 0x2D, null);
        registerPacketOut(Status.PLAY, 0x2E, PacketPlayOutPlayerListItem107.class);
        registerPacketOut(Status.PLAY, 0x2F, PacketPlayOutPlayerPositionAndLook107.class);
    }

    @Override
    public int[] getVersions() {
        return new int[]{338};
    }
}
