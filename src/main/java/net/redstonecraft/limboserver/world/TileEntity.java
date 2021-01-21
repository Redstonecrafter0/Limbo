package net.redstonecraft.limboserver.world;

import net.redstonecraft.limboserver.world.nbt.CompoundTag;

public abstract class TileEntity {
    private final BlockPosition blockPosition;
    private final CompoundTag data;

    public TileEntity(CompoundTag data) {
        this.data = data;
        blockPosition = new BlockPosition(data.get("x").toIntTag().getValue(), data.get("y").toIntTag().getValue(), data.get("z").toIntTag().getValue());
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }

    public abstract String getId();

    public CompoundTag getData() {
        return data;
    }
}
