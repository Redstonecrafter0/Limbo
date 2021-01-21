package net.redstonecraft.limboserver.packets;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.world.Chunk;
import net.redstonecraft.limboserver.world.SignTileEntity;
import net.redstonecraft.limboserver.packets.in.PacketIn;
import net.redstonecraft.limboserver.packets.out.PacketOut;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractProtocol {

    protected final NetworkManager networkManager;
    private final Map<Status, Map<Integer, Class<? extends PacketIn>>> registryIn;
    private final Map<Status, Map<Integer, Class<? extends PacketOut>>> registryOut;

    public AbstractProtocol(NetworkManager networkManager) {
        this.networkManager = networkManager;
        registryIn = new HashMap<>();
        registryOut = new HashMap<>();
        for (Status status : Status.values()) {
            registryIn.put(status, new HashMap<>());
        }
        for (Status status : Status.values()) {
            registryOut.put(status, new HashMap<>());
        }
    }

    protected void registerPacketIn(Status status, int id, Class<? extends PacketIn> packetClass) {
        if (packetClass == null) {
            registryIn.get(status).remove(id);
        } else {
            registryIn.get(status).put(id, packetClass);
        }
    }

    protected void registerPacketOut(Status status, int id, Class<? extends PacketOut> packetClass) {
        if (packetClass == null) {
            registryOut.get(status).remove(id);
        } else {
            registryOut.get(status).put(id, packetClass);
        }
    }

    public abstract void sendKeepAlive(PlayerConnection playerConnection, int id);

    public abstract void sendChunk(PlayerConnection playerConnection, Chunk chunk);

    public abstract void sendSign(PlayerConnection playerConnection, SignTileEntity signTileEntity);

    public abstract void disconnect(PlayerConnection playerConnection, String message);

    public abstract void sendJoinGame(PlayerConnection playerConnection, int entityId, byte gameMode, int dimension, byte difficulty, byte maxPlayers, String levelType, boolean debugInfo);

    public abstract void sendPosition(PlayerConnection playerConnection, double x, double y, double z, float yaw, float pitch);

    public abstract void sendAddPlayerList(PlayerConnection playerConnection, UUID uuid, String name);

    public abstract void sendTabList(PlayerConnection playerConnection, String header, String footer);

    public abstract void sendMessage(PlayerConnection playerConnection, String message);

    public abstract int[] getVersions();

    public final Class<? extends PacketIn> getPacketInById(int id, Status status) {
        return registryIn.get(status).get(id);
    }

    public final int getPacketOutByClass(Class<? extends PacketOut> packetClass, Status status) {
        for (Map.Entry<Integer, Class<? extends PacketOut>> entry : registryOut.get(status).entrySet()) {
            if (entry.getValue().equals(packetClass)) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
