package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayOutPlayerPositionAndLook47 implements PacketOut {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final byte flags;

    public PacketPlayOutPlayerPositionAndLook47(double x, double y, double z, float yaw, float pitch, byte flags) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeDouble(x);
        packetSerializer.writeDouble(y);
        packetSerializer.writeDouble(z);
        packetSerializer.writeFloat(yaw);
        packetSerializer.writeFloat(pitch);
        packetSerializer.writeByte(flags);
    }
}
