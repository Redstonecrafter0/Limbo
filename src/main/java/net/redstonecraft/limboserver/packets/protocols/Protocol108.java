package net.redstonecraft.limboserver.packets.protocols;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutJoinGame108;

public class Protocol108 extends Protocol107 {

    public Protocol108(NetworkManager networkManager) {
        super(networkManager);
        registerPacketOut(Status.PLAY, 0x23, PacketPlayOutJoinGame108.class);
    }

    @Override
    public void sendJoinGame(PlayerConnection playerConnection, int entityId, byte gameMode, int dimension, byte difficulty, byte maxPlayers, String levelType, boolean debugInfo) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutJoinGame108(entityId, gameMode, dimension, difficulty, maxPlayers, levelType, debugInfo));
    }

    @Override
    public int[] getVersions() {
        return new int[]{108, 109};
    }
}
