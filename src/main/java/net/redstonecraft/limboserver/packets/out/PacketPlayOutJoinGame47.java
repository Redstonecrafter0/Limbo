package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayOutJoinGame47 implements PacketOut {

    private final int entityId;
    private final byte gameMode;
    private final byte dimension;
    private final byte difficulty;
    private final byte maxPlayers;
    private final String levelType;
    private final boolean debugInfo;

    public PacketPlayOutJoinGame47(int entityId, byte gameMode, byte dimension, byte difficulty, byte maxPlayers, String levelType, boolean debugInfo) {
        this.entityId = entityId;
        this.gameMode = gameMode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.levelType = levelType;
        this.debugInfo = debugInfo;
    }

    @Override
    public void write(PacketSerializer packetSerializer) throws IOException {
        packetSerializer.writeInt(entityId);
        packetSerializer.writeByte(gameMode);
        packetSerializer.writeByte(dimension);
        packetSerializer.writeByte(difficulty);
        packetSerializer.writeByte(maxPlayers);
        packetSerializer.writeString(levelType);
        packetSerializer.writeBoolean(debugInfo);
    }
}
