package net.redstonecraft.limboserver;

import net.redstonecraft.limboserver.packets.AbstractProtocol;
import net.redstonecraft.limboserver.packets.Status;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.UUID;

public class PlayerConnection {
    private final SocketChannel socket;
    private ByteBuffer byteBuffer;
    private AbstractProtocol protocol;
    private Status status;
    private String userName;
    private UUID uuid;
    private InetSocketAddress inetAddress;
    private int protocolId;
    private long lastKeepAlive;

    public PlayerConnection(NetworkManager networkManager, SocketChannel socket) throws IOException {
        this.socket = socket;
        byteBuffer = ByteBuffer.allocate(NetworkManager.MAX_BUFFER_SIZE);
        protocol = networkManager.getProtocolByVersionId(-1);
        status = Status.HANDSHAKE;
        userName = "";
        protocolId = -1;
        lastKeepAlive = System.currentTimeMillis();
        uuid = UUID.randomUUID();
        inetAddress = (InetSocketAddress) socket.getRemoteAddress();
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public AbstractProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(AbstractProtocol protocol) {
        this.protocol = protocol;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public void setProtocolId(int protocolId) {
        this.protocolId = protocolId;
    }

    public int getProtocolId() {
        return protocolId;
    }

    public long getLastKeepAlive() {
        return lastKeepAlive;
    }

    public void keepAlive() {
        lastKeepAlive = System.currentTimeMillis();
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public void setUniqueId(UUID uuid) {
        this.uuid = uuid;
    }

    public InetSocketAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetSocketAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

}
