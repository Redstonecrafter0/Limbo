package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;
import java.util.UUID;

public class PacketLoginOutSuccess implements PacketOut {

    private final UUID uuid;
    private final String userName;

    public PacketLoginOutSuccess(UUID uuid, String userName) {
        this.uuid = uuid;
        this.userName = userName;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeString(uuid.toString());
        packetSerializer.writeString(userName);
    }
}
