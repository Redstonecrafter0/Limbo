package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.Main;
import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;
import java.util.UUID;

public class PacketPlayOutPlayerListItem47 implements PacketOut {

    private final UUID uuid;
    private final String name;

    public PacketPlayOutPlayerListItem47(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeVarInt(0);
        packetSerializer.writeVarInt(1);
        packetSerializer.writeUniqueId(uuid);
        packetSerializer.writeString(name);
        packetSerializer.writeVarInt(0);
        packetSerializer.writeVarInt(Main.getConfig().getGameMode());
        packetSerializer.writeVarInt(1);
        packetSerializer.writeBoolean(true);
        packetSerializer.writeString(name);
    }
}
