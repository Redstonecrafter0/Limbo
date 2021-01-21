package net.redstonecraft.limboserver.world;

import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chunk {

    private static final int CHUNK_SIZE = 196864;
    private final int x;
    private final int z;
    private final byte[] map;
    private final long[][] mapWithPalette;
    private final List<TileEntity> tileEntities;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
        map = new byte[Chunk.CHUNK_SIZE];
        mapWithPalette = new long[16][832];
        tileEntities = new ArrayList<>();
        Arrays.fill(map, (byte)0);
        for (long[] tab : mapWithPalette) {
            Arrays.fill(tab, 0L);
        }
    }

    public void setBlock(int x, int y, int z, int id, int meta) {
        int index = x + y * 16 * 16 + z * 16;
        index *= 2;
        map[index] = ((byte) ((byte) (id << 4) | meta));
        map[(index + 1)] = ((byte) (id >> 4));
        int section = y / 16;
        index = x + z * 16 + y % 16 * 16 * 16;
        index *= 13;
        int l = index / 64;
        index = index % 64;
        for (int i = 0; i < 13; ++i) {
            if (index == 64) {
                index = 0;
                l++;
            }
            long value;
            if (i < 4) {
                value = (meta & (1L << i)) != 0 ? 1 : 0;
            } else {
                value = (id & (1L << (i - 4))) != 0 ? 1 : 0;
            }
            mapWithPalette[section][l] = mapWithPalette[section][l] & ~(1L << index);
            mapWithPalette[section][l] = mapWithPalette[section][l] | (value << index);
            index++;
        }
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public byte[] getMap() {
        return map;
    }

    public byte[] getMapWithPalette() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PacketSerializer packetSerializer = new PacketSerializer(byteArrayOutputStream);
        for (int i = 0; i < 16; ++i) {
            packetSerializer.writeByte((byte) 13);
            packetSerializer.writeVarInt(0);
            packetSerializer.writeVarInt(mapWithPalette[i].length);
            for (long value : mapWithPalette[i]) {
                packetSerializer.writeLong(value);
            }
            for (int j = 0; j < 2048; ++j) {
                packetSerializer.writeByte((byte) 0);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void sendTileEntities(PlayerConnection player) {
        tileEntities.stream().filter(tileEntity -> (tileEntity instanceof SignTileEntity)).forEach(tileEntity -> player.getProtocol().sendSign(player, (SignTileEntity) tileEntity));
    }

    public void addTileEntity(TileEntity tileEntity) {
        tileEntities.add(tileEntity);
    }
}
