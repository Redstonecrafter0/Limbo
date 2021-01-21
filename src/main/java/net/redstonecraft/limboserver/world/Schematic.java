package net.redstonecraft.limboserver.world;

import net.redstonecraft.limboserver.world.nbt.ByteArrayTag;
import net.redstonecraft.limboserver.world.nbt.CompoundTag;
import net.redstonecraft.limboserver.world.nbt.NamedTag;

public class Schematic {

    public static World toWorld(CompoundTag nbt) {
        short width = nbt.get("Width").toShortTag().getValue();
        short length = nbt.get("Length").toShortTag().getValue();
        short height = nbt.get("Height").toShortTag().getValue();
        byte[] blocks = ((ByteArrayTag) nbt.get("Blocks")).getBytes();
        byte[] blocksData = ((ByteArrayTag) nbt.get("Data")).getBytes();
        World world = new World(width, length);
        int y;
        int z;
        for (int x = 0; x < width; x++) {
            for (y = 0; y < height; y++) {
                for (z = 0; z < length; z++) {
                    int blockIndex = y * width * length + z * width + x;
                    world.setBlock(x, y, z, blocks[blockIndex] < 0 ? blocks[blockIndex] + 256 : blocks[blockIndex], blocksData[blockIndex]);
                }
            }
        }
        for (NamedTag tag : nbt.get("TileEntities").toListTag().getTags()) {
            SignTileEntity sign = new SignTileEntity((CompoundTag)tag);
            world.getChunkAtWorldPos(sign.getBlockPosition().getX(), sign.getBlockPosition().getZ()).addTileEntity(sign);
        }
        return world;
    }

}
