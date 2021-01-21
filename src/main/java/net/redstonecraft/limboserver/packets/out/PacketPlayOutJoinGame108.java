package net.redstonecraft.limboserver.packets.out;

import net.redstonecraft.limboserver.packets.PacketSerializer;

import java.io.IOException;

public class PacketPlayOutJoinGame108 implements PacketOut {

    private final int entityId;
    private final byte gameMode;
    private final int dimension;
    private final byte difficulty;
    private final byte maxPlayers;
    private final String levelType;
    private final boolean debugInfo;

    public PacketPlayOutJoinGame108(int entityId, byte gameMode, int dimension, byte difficulty, byte maxPlayers, String levelType, boolean debugInfo) {
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
        packetSerializer.writeInt(dimension);
        packetSerializer.writeByte(difficulty);
        packetSerializer.writeByte(maxPlayers);
        packetSerializer.writeString(levelType);
        packetSerializer.writeBoolean(debugInfo);
    }
}
