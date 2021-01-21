package net.redstonecraft.limboserver.packets.in;

import net.redstonecraft.limboserver.Main;
import net.redstonecraft.limboserver.NetworkManager;
import net.redstonecraft.limboserver.PlayerConnection;
import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.util.ChatColor;

import java.io.IOException;

public class PacketPlayInChatMessage47 implements PacketIn {

    private String message;

    @Override
    public void readPacket(PacketSerializer packetSerializer) throws IOException {
        message = packetSerializer.readString();
        if (message.length() > 119) {
            message = message.substring(119);
        }
    }

    @Override
    public void handlePacket(NetworkManager networkManager, PlayerConnection playerConnection) {
        if (message.startsWith("/")) {
            playerConnection.getProtocol().sendMessage(playerConnection, ChatColor.renderColors("&cBefehl nicht gefunden."));
        } else {
            playerConnection.getProtocol().sendMessage(playerConnection, Main.getConfig().formatChatMessage(playerConnection, message));
        }
    }

}
