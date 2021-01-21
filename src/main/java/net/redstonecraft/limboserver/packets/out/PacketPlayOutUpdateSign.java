package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.world.SignTileEntity;

import java.io.IOException;

public class PacketPlayOutUpdateSign implements PacketOut {

    private final SignTileEntity signTileEntity;

    public PacketPlayOutUpdateSign(SignTileEntity signTileEntity) {
        this.signTileEntity = signTileEntity;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writePosition(signTileEntity.getBlockPosition());
        packetSerializer.writeString(signTileEntity.getText1());
        packetSerializer.writeString(signTileEntity.getText2());
        packetSerializer.writeString(signTileEntity.getText3());
        packetSerializer.writeString(signTileEntity.getText4());
    }
}
