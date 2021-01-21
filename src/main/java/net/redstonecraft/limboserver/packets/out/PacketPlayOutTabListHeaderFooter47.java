package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayOutTabListHeaderFooter47 implements PacketOut {

    private final String header;
    private final String footer;

    public PacketPlayOutTabListHeaderFooter47(String header, String footer) {
        this.header = header;
        this.footer = footer;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeString(header);
        packetSerializer.writeString(footer);
    }
}
