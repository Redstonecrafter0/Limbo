package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.world.TileEntity;
import net.redstonecraft.limboserver.world.nbt.NBTTag;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PacketPlayOutUpdateBlockEntity110 implements PacketOut {

    private final TileEntity tileEntity;
    private final byte action;

    public PacketPlayOutUpdateBlockEntity110(TileEntity tileEntity, byte action) {
        this.tileEntity = tileEntity;
        this.action = action;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writePosition(tileEntity.getBlockPosition());
        packetSerializer.writeByte(action);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        NBTTag.writeTag(outputStream, tileEntity.getData(), true);
        for (byte b : outputStream.toByteArray()) {
            packetSerializer.writeByte(b);
        }
    }
}
