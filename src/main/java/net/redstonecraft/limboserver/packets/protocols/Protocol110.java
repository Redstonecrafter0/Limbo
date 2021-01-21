package net.redstonecraft.limboserver.packets.protocols;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutChunkData110;
import net.redstonecraft.limboserver.packets.out.PacketPlayOutUpdateBlockEntity110;
import net.redstonecraft.limboserver.world.Chunk;
import net.redstonecraft.limboserver.world.SignTileEntity;

public class Protocol110 extends Protocol108 {

    public Protocol110(NetworkManager networkManager) {
        super(networkManager);
        registerPacketOut(Status.PLAY, 0x09, PacketPlayOutUpdateBlockEntity110.class);
        registerPacketOut(Status.PLAY, 0x20, PacketPlayOutChunkData110.class);
        registerPacketOut(Status.PLAY, 0x46, null);
    }

    @Override
    public int[] getVersions() {
        return new int[]{110};
    }

    @Override
    public void sendSign(PlayerConnection playerConnection, SignTileEntity signTileEntity) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutUpdateBlockEntity110(signTileEntity, (byte)9));
    }

    @Override
    public void sendChunk(PlayerConnection playerConnection, Chunk chunk) {
        networkManager.sendPacket(playerConnection, new PacketPlayOutChunkData110(chunk));
    }
}
