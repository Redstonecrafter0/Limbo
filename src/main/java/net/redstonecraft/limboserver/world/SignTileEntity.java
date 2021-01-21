package net.redstonecraft.limboserver.world;

import net.redstonecraft.limboserver.world.nbt.CompoundTag;

public class SignTileEntity extends TileEntity {

    private final String text1;
    private final String text2;
    private final String text3;
    private final String text4;

    public SignTileEntity(CompoundTag data) {
        super(data);
        this.text1 = data.get("Text1").toStringTag().getValue();
        this.text2 = data.get("Text2").toStringTag().getValue();
        this.text3 = data.get("Text3").toStringTag().getValue();
        this.text4 = data.get("Text4").toStringTag().getValue();
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    public String getText4() {
        return text4;
    }

    public String getId() {
        return "Sign";
    }
}
