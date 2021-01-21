package net.redstonecraft.limboserver.world;

public class World {
    private final Chunk[][] chunks;

    public World(int width, int length) {
        chunks = new Chunk[width][length];
    }

    public void setBlock(int x, int y, int z, int id, byte data) {
        Chunk chunk = chunks[(x >> 4)][(z >> 4)];
        if (chunk == null) {
            chunk = new Chunk(x >> 4, z >> 4);
            chunks[(x >> 4)][(z >> 4)] = chunk;
        }
        chunk.setBlock(x & 0xF, y, z & 0xF, id, data);
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public Chunk getChunkAtWorldPos(int x, int z) {
        return chunks[(x >> 4)][(z >> 4)];
    }
}
