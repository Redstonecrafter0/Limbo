package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.Main;
import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutRespawn47;
import net.redstonecraft.limboserver.world.Chunk;
import net.redstonecraft.limboserver.packets.out.PacketLoginOutSuccess;

import java.io.IOException;

public class PacketLoginInStart implements PacketIn {

    private String userName;

    @Override
    public void readPacket(PacketSerializer packetSerializer) throws IOException {
        userName = packetSerializer.readString();
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
        playerConnection.setName(userName);
        networkManager.sendPacket(playerConnection, new PacketLoginOutSuccess(playerConnection.getUniqueId(), this.userName));
        playerConnection.setStatus(Status.PLAY);
        playerConnection.getProtocol().sendJoinGame(playerConnection, 1, networkManager.getLimboConfiguration().getGameMode(), networkManager.getLimboConfiguration().getDimension(), (byte)0, (byte)1, "default", networkManager.getLimboConfiguration().isReducedDebugInfo());
        new Thread(() -> {
            try {
                Thread.sleep(100);
                for (Chunk[] tab : networkManager.getWorld().getChunks()) {
                    for (Chunk chunk : tab) {
                        if (chunk != null) {
                            playerConnection.getProtocol().sendChunk(playerConnection, chunk);
                            chunk.sendTileEntities(playerConnection);
                        }
                    }
                }
                Thread.sleep(100);
                playerConnection.getProtocol().sendPosition(playerConnection, networkManager.getLimboConfiguration().getSpawnX(), networkManager.getLimboConfiguration().getSpawnY(), networkManager.getLimboConfiguration().getSpawnZ(), networkManager.getLimboConfiguration().getSpawnYaw(), networkManager.getLimboConfiguration().getSpawnPitch());
                playerConnection.getProtocol().sendAddPlayerList(playerConnection, playerConnection.getUniqueId(), userName);
                playerConnection.getProtocol().sendTabList(playerConnection, Main.getConfig().getTabHeader(), Main.getConfig().getTabFooter());
                Thread.sleep(100);
                networkManager.sendPacket(playerConnection, new PacketPlayOutRespawn47(networkManager.getLimboConfiguration().getDimension()));
            } catch (InterruptedException ignored) {
            }
        }).start();
    }
}
