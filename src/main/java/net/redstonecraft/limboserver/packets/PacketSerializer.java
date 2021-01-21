package net.redstonecraft.limboserver.packets;

import net.redstonecraft.limboserver.world.BlockPosition;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public class PacketSerializer {
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public PacketSerializer(InputStream inputStream) {
        this.inputStream = inputStream;
        outputStream = null;
    }

    public PacketSerializer(OutputStream outputStream) {
        inputStream = null;
        this.outputStream = outputStream;
    }

    private void ensureInputStream() {
        if (inputStream == null) {
            throw new IllegalStateException("Trying to read an output PacketSerializer");
        }
    }

    private void ensureOutputStream() {
        if (outputStream == null) {
            throw new IllegalStateException("Trying to write on input PacketSerializer");
        }
    }

    public int readVarInt() throws IOException {
        ensureInputStream();
        int out = 0;
        int bytes = 0;
        int in;
        do {
            in = readByte();
            out |= (in & 0x7F) << (bytes++ * 7);
            if (bytes > 5) {
                throw new IOException("VarInt too big");
            }
        } while ((in & 0x80) == 0x80);
        return out;
    }

    private byte[] readByteArray() throws IOException {
        ensureInputStream();
        int size = readVarInt();
        byte[] bytes = new byte[size];
        int ret = Objects.requireNonNull(inputStream).read(bytes);
        if (ret != size) {
            throw new IOException("Incorrect byte array");
        }
        return bytes;
    }

    public byte readByte() throws IOException {
        ensureInputStream();
        return (byte) Objects.requireNonNull(inputStream).read();
    }

    public short readShort() throws IOException {
        ensureInputStream();
        short result = readByte();
        result *= 256;
        result += readByte();
        return result;
    }

    public String readString() throws IOException {
        ensureInputStream();
        return new String(readByteArray(), StandardCharsets.UTF_8);
    }

    public long readLong() throws IOException {
        ensureInputStream();
        long result = readByte();
        for (int i = 0; i < 7; i++)
        {
            result *= 256;
            result += readByte();
        }
        return result;
    }

    public boolean readBoolean() throws IOException {
        ensureInputStream();
        byte b = readByte();
        switch (b) {
            case 0x00:
                return false;
            case 0x01:
                return true;
            default:
                throw new IOException();
        }
    }

    public double readDouble() throws IOException {
        ensureInputStream();
        byte[] arr = new byte[8];
        for (int i = 0; i < 8; i++) {
            arr[i] = readByte();
        }
        return ByteBuffer.wrap(arr).getDouble();
    }

    public void writeUniqueId(UUID uuid) throws IOException {
        ByteBuffer b = ByteBuffer.wrap(new byte[16]);
        b.putLong(uuid.getMostSignificantBits());
        b.putLong(uuid.getLeastSignificantBits());
        Objects.requireNonNull(outputStream).write(b.array());
    }

    public void writeVarInt(int value) throws IOException {
        ensureOutputStream();
        int part;
        do {
            part = value & 0x7F;
            value >>>= 7;
            if (value != 0) {
                part |= 0x80;
            }
            Objects.requireNonNull(outputStream).write(part);
        } while (value != 0);
    }

    public void writeByteArray(byte[] bytes) throws IOException {
        ensureOutputStream();
        writeVarInt(bytes.length);
        Objects.requireNonNull(outputStream).write(bytes);
    }

    public void writeString(String string) throws IOException {
        ensureOutputStream();
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        writeByteArray(bytes);
    }

    public void writeInt(int value) throws IOException {
        ensureOutputStream();
        Objects.requireNonNull(outputStream).write(ByteBuffer.allocate(4).putInt(value).array());
    }

    public void writeShort(short value) throws IOException {
        ensureOutputStream();
        Objects.requireNonNull(outputStream).write(ByteBuffer.allocate(2).putShort(value).array());
    }

    public void writeLong(long value) throws IOException {
        ensureOutputStream();
        Objects.requireNonNull(outputStream).write(ByteBuffer.allocate(8).putLong(value).array());
    }

    public void writeByte(byte value) throws IOException {
        ensureOutputStream();
        Objects.requireNonNull(outputStream).write(value);
    }

    public void writeBoolean(boolean value) throws IOException {
        ensureOutputStream();
        Objects.requireNonNull(outputStream).write(value ? 0x01 : 0x00);
    }

    public void writeDouble(double value) throws IOException {
        ensureOutputStream();
        Objects.requireNonNull(outputStream).write(ByteBuffer.allocate(8).putDouble(value).array());
    }

    public void writeFloat(float value) throws IOException {
        ensureOutputStream();
        Objects.requireNonNull(outputStream).write(ByteBuffer.allocate(4).putFloat(value).array());
    }

    public void writePosition(BlockPosition blockPosition) throws IOException {
        this.ensureOutputStream();
        this.writeLong(((long) (blockPosition.getX() & 0x3FFFFFF) << 38) | ((long) (blockPosition.getY() & 0xFFF) << 26) | (blockPosition.getZ() & 0x3FFFFFF));
    }

    public int available() throws IOException {
        ensureInputStream();
        return Objects.requireNonNull(inputStream).available();
    }
}
