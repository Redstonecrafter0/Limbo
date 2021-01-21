package net.redstonecraft.limboserver.packets.protocols;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.AbstractProtocol;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.in.*;
import net.redstonecraft.limboserver.packets.out.*;
import net.redstonecraft.limboserver.world.Chunk;
import net.redstonecraft.limboserver.world.SignTileEntity;

import java.util.UUID;

public class Protocol107 extends AbstractProtocol {

    public Protocol107(NetworkManager networkManager) {
        super(networkManager);
        registerPacketIn(Status.STATUS, 0x00, PacketStatusInRequest.class);
        registerPacketIn(Status.STATUS, 0x01, PacketStatusInPing.class);
        registerPacketOut(Status.STATUS, 0x00, PacketStatusOutResponse.class);
        registerPacketOut(Status.STATUS, 0x01, PacketStatusOutPong.class);
        registerPacketIn(Status.LOGIN, 0x00, PacketLoginInStart.class);
        registerPacketOut(Status.LOGIN, 0x00, PacketOutDisconnect.class);
        registerPacketOut(Status.LOGIN, 0x02, PacketLoginOutSuccess.class);
        registerPacketOut(Status.PLAY, 0x1A, PacketOutDisconnect.class);
        registerPacketOut(Status.PLAY, 0x1F, PacketPlayOutKeepAlive47.class);
        registerPacketOut(Status.PLAY, 0x20, PacketPlayOutChunkData107.class);
        registerPacketOut(Status.PLAY, 0x23, PacketPlayOutJoinGame47.class);
        registerPacketOut(Status.PLAY, 0x2E, PacketPlayOutPlayerPositionAndLook107.class);
        registerPacketOut(Status.PLAY, 0x46, PacketPlayOutUpdateSign.class);
        registerPacketOut(Status.PLAY, 0x2D, PacketPlayOutPlayerListItem107.class);
        registerPacketOut(Status.PLAY, 0x48, PacketPlayOutTabListHeaderFooter47.class);
        registerPacketOut(Status.PLAY, 0x0F, PacketPlayOutChatMessage47.class);
        registerPacketOut(Status.PLAY, 0x33, PacketPlayOutRespawn47.class);
        registerPacketIn(Status.PLAY, 0x00, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x01, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x02, PacketPlayInChatMessage47.class);
        registerPacketIn(Status.PLAY, 0x03, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x04, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x05, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x06, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x07, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x08, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x09, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x0A, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x0B, PacketPlayInKeepAlive47.class);
        registerPacketIn(Status.PLAY, 0x0C, PacketPlayInMove47.class);
        registerPacketIn(Status.PLAY, 0x0D, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x0E, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x0F, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x10, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x11, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x12, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x13, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x14, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x15, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x16, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x17, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x18, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x19, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x1A, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x1B, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x1C, PacketInIgnored.class);
        registerPacketIn(Status.PLAY, 0x1D, PacketInIgnored.class);
    }

    @Override
    public int[] getVersions() {
        return new int[]{107};
    }

    @Override
    public void disconnect(PlayerConnection playerConnection, String message) {
        networkManager.sendPacket(playerConnection, new PacketOutDisconnect(message));
    }

    @Override
    public void sendJoinGame(PlayerConnection playerConnection, int entityId, byte gameMode, int dimension, byte difficulty, byte maxPlayers, String levelType, boolean debugInfo) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutJoinGame47(entityId, gameMode, (byte)dimension, difficulty, maxPlayers, levelType, debugInfo));
    }

    @Override
    public void sendPosition(PlayerConnection playerConnection, double x, double y, double z, float yaw, float pitch) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutPlayerPositionAndLook107(x, y, z, yaw, pitch, (byte)0, 0));
    }

    @Override
    public void sendAddPlayerList(PlayerConnection playerConnection, UUID uuid, String name) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutPlayerListItem107(uuid, name));
    }

    @Override
    public void sendTabList(PlayerConnection playerConnection, String header, String footer) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutTabListHeaderFooter47(header, footer));
    }

    @Override
    public void sendMessage(PlayerConnection playerConnection, String message) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutChatMessage47(message));
    }

    @Override
    public void sendKeepAlive(PlayerConnection playerConnection, int id) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutKeepAlive47(id));
    }

    @Override
    public void sendChunk(PlayerConnection playerConnection, Chunk chunk) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutChunkData107(chunk));
    }

    @Override
    public void sendSign(PlayerConnection playerConnection, SignTileEntity signTileEntity) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutUpdateSign(signTileEntity));
    }
}

