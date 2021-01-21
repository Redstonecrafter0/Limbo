package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.packets.out.PacketStatusOutResponse;

public class PacketStatusInRequest implements PacketIn {

    @Override
    public void readPacket(PacketSerializer packetSerializer) {
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
        networkManager.sendPacket(playerConnection, new PacketStatusOutResponse("{\n" +
                "    \"version\": {\n" +
                "        \"name\": \"LimboServer\",\n" +
                "        \"protocol\": " + playerConnection.getProtocolId() + "\n" +
                "    },\n" +
                "    \"players\": {\n" +
                "        \"max\": 9999,\n" +
                "        \"online\": 0,\n" +
                "        \"sample\": [\n" +
                "        ]\n" +
                "    },\t\n" +
                "    \"description\": {\n" +
                "        \"text\": \"LimboServer by Redstonecrafter0\"\n" +
                "    }\n" +
                "}"));
    }
}
