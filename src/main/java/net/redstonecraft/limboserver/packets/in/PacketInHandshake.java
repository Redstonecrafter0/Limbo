package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.Main;
import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.AbstractProtocol;
import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.util.UUIDConverter;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PacketInHandshake implements PacketIn {
    private int version;
    private String serverAddress;
    private short serverPort;
    private int nextState;

    @Override
    public void readPacket(PacketSerializer packetSerializer) throws IOException {
        version = packetSerializer.readVarInt();
        serverAddress = packetSerializer.readString();
        serverPort = packetSerializer.readShort();
        nextState = packetSerializer.readVarInt();
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
        if (version < 47) {
            networkManager.disconnect(playerConnection, "Outdated Client");
            return;
        }
        AbstractProtocol abstractProtocol = networkManager.getProtocolByVersionId(version);
        if (abstractProtocol == null) {
            Main.getLogger().warning("Version " + version + " not supported");
            networkManager.disconnect(playerConnection, "Version " + version + " not supported");
            return;
        }
        Status status = nextState == 1 ? Status.STATUS : nextState == 2 ? Status.LOGIN : null;
        if (status == null) {
            Main.getLogger().warning("Received invalid status " + nextState);
            networkManager.disconnect(playerConnection, "State " + nextState + " incorrect");
            return;
        }
        if (status == Status.LOGIN && networkManager.getLimboConfiguration().getMaxSlots() >= 0 && networkManager.getConnectedPlayers() >= networkManager.getLimboConfiguration().getMaxSlots()) {
            networkManager.disconnect(playerConnection, "Server is full");
            return;
        }
        playerConnection.setProtocol(abstractProtocol);
        playerConnection.setProtocolId(version);
        playerConnection.setStatus(status);
        String[] data = serverAddress.split("\000");
        InetSocketAddress bungeeAddress = null;
        if (data.length == 3 || data.length == 4) {
            serverAddress = data[0];
            bungeeAddress = playerConnection.getInetAddress();
            playerConnection.setInetAddress(new InetSocketAddress(data[1], playerConnection.getInetAddress().getPort()));
            playerConnection.setUniqueId(UUIDConverter.fromString(data[2]));
        }
        if (status == Status.LOGIN) {
            Main.getLogger().info("Connection from [" + playerConnection.getInetAddress().getHostString() + "/" + playerConnection.getName() + "]" + (bungeeAddress == null ? "" : " (BungeeCord)") + " Protocol " + version);
        }
    }
}
