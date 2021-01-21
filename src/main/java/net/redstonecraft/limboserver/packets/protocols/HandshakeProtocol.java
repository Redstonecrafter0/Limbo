package net.redstonecraft.limboserver.packets.protocols;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.AbstractProtocol;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.in.PacketInHandshake;
import net.redstonecraft.limboserver.packets.out.PacketOutDisconnect;
import net.redstonecraft.limboserver.world.Chunk;
import net.redstonecraft.limboserver.world.SignTileEntity;

import java.util.UUID;

public class HandshakeProtocol extends AbstractProtocol {

    public HandshakeProtocol(NetworkManager networkManager) {
        super(networkManager);
        registerPacketIn(Status.HANDSHAKE, 0x00, PacketInHandshake.class);
        registerPacketOut(Status.HANDSHAKE, 0x00, PacketOutDisconnect.class);
    }

    @Override
    public void disconnect(PlayerConnection playerConnection, String message) {
        networkManager.sendPacket(playerConnection, new PacketOutDisconnect(message));
    }

    @Override
    public void sendJoinGame(PlayerConnection playerConnection, int entityId, byte gameMode, int dimension, byte difficulty, byte maxPlayers, String levelType, boolean debugInfo) {
    }

    @Override
    public void sendPosition(PlayerConnection playerConnection, double x, double y, double z, float yaw, float pitch) {
    }

    @Override
    public void sendAddPlayerList(PlayerConnection playerConnection, UUID uuid, String name) {
    }

    @Override
    public void sendTabList(PlayerConnection playerConnection, String header, String footer) {
    }

    @Override
    public void sendMessage(PlayerConnection playerConnection, String message) {
    }

    @Override
    public void sendKeepAlive(PlayerConnection playerConnection, int id) {
    }

    @Override
    public void sendChunk(PlayerConnection playerConnection, Chunk chunk) {
    }

    @Override
    public void sendSign(PlayerConnection playerConnection, SignTileEntity signTileEntity) {
    }

    @Override
    public int[] getVersions() {
        return new int[]{-1};
    }
}
