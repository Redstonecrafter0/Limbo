package net.redstonecraft.limboserver;

import net.redstonecraft.limboserver.packets.AbstractProtocol;
import net.redstonecraft.limboserver.packets.PacketSerializer;
import net.redstonecraft.limboserver.packets.Status;
import net.redstonecraft.limboserver.packets.in.PacketIn;
import net.redstonecraft.limboserver.packets.out.PacketOut;
import net.redstonecraft.limboserver.packets.protocols.*;
import net.redstonecraft.limboserver.world.World;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class NetworkManager {

    public static final int MAX_BUFFER_SIZE = 4096;

    private final Configuration limboConfiguration;
    private final World world;
    private Selector selector;
    private ServerSocketChannel serverSocket;
    private final List<PlayerConnection> playerConnections = new LinkedList<>();
    private final List<AbstractProtocol> protocols = new ArrayList<>();
    private long lastKeepAlive = System.currentTimeMillis();
    private int keepAliveId = 0;

    public NetworkManager(World world, Configuration limboConfiguration) {
        this.limboConfiguration = limboConfiguration;
        this.world = world;
        protocols.add(new HandshakeProtocol(this));
        protocols.add(new Protocol47(this));
        protocols.add(new Protocol107(this));
        protocols.add(new Protocol108(this));
        protocols.add(new Protocol110(this));
        protocols.add(new Protocol335(this));
        protocols.add(new Protocol338(this));
        protocols.add(new Protocol340(this));
    }

    public void bind(String ip, short port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(ip, port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, serverSocket.validOps(), null);
    }

    public void tick() throws IOException {
        long time = System.currentTimeMillis();
        if (time >= lastKeepAlive + 10000L) {
            keepAliveId++;
            if (keepAliveId > 99999) {
                keepAliveId = 0;
            }
            lastKeepAlive = time;
            long t = time - 15000L;
            List<PlayerConnection> connections = new ArrayList<>(playerConnections);
            connections.stream().filter(playerConnection -> playerConnection.getLastKeepAlive() < t).forEach(playerConnection -> disconnect(playerConnection, "Timed out"));
            connections.forEach(playerConnection -> playerConnection.getProtocol().sendKeepAlive(playerConnection, keepAliveId));
        }
        time += 10000L;
        time -= lastKeepAlive;
        int n = selector.select(time);
        if (n == 0) {
            return;
        }
        for (SelectionKey key : selector.selectedKeys()) {
            if (!key.isValid()) {
                continue;
            }
            if (key.isAcceptable()) {
                SocketChannel channel = serverSocket.accept();
                if (channel != null) {
                    channel.configureBlocking(false);
                    PlayerConnection playerConnection = new PlayerConnection(this, channel);
                    playerConnections.add(playerConnection);
                    channel.register(selector, channel.validOps(), null);
                }
            } else if (key.isReadable()) {
                readPacket((SocketChannel) key.channel());
                System.out.println("------------------------");
                System.out.println(playerConnections.size());
                for (PlayerConnection i : playerConnections) {
                    System.out.println(i.getInetAddress().getHostName() + ":" + i.getInetAddress().getPort() + " " + i.getUniqueId().toString() + " " + i.getName());
                }
                System.out.println("------------------------");
            }
        }
        selector.selectedKeys().clear();
    }

    private void readPacket(SocketChannel socket) {
        PlayerConnection playerConnection = playerConnections.stream().filter(connection -> connection.getSocket().equals(socket)).findFirst().orElse(null);
        if (playerConnection == null) {
            Main.getLogger().severe("Received data on channel not registered. (" + socket.socket().getInetAddress().getHostAddress() + ").");
            return;
        }
        try {
            ByteBuffer byteBuffer = playerConnection.getByteBuffer();
            if (socket.read(byteBuffer) == -1) {
                disconnect(playerConnection, null);
                return;
            }
            PacketSerializer packetSerializer = new PacketSerializer(new ByteArrayInputStream(byteBuffer.array()));
            byte[] data;
            int size = packetSerializer.available();
            try {
                int s = packetSerializer.readVarInt();
                if (s > NetworkManager.MAX_BUFFER_SIZE)
                    disconnect(playerConnection, "Invalid packet, too big");
                data = new byte[s];
                for (int i = 0; i < s; i++) {
                    data[i] = packetSerializer.readByte();
                }
                if (data.length == 0) {
                    return;
                }
            } catch (EOFException ignored) {
                return;
            }
            size -= packetSerializer.available();
            ByteBuffer tmp = ByteBuffer.allocate(NetworkManager.MAX_BUFFER_SIZE);
            for (int i = size; i < byteBuffer.position(); i++) {
                tmp.put(byteBuffer.get(i));
            }
            playerConnection.setByteBuffer(tmp);
            packetSerializer = new PacketSerializer(new ByteArrayInputStream(data));
            int packetId = packetSerializer.readVarInt();
            Class<? extends PacketIn> packetInClass = playerConnection.getProtocol().getPacketInById(packetId, playerConnection.getStatus());
            if (packetInClass == null) {
                disconnect(playerConnection, "Unknown packet " + packetId);
                return;
            }
            PacketIn packetIn = packetInClass.newInstance();
            packetIn.readPacket(packetSerializer);
            packetIn.handlePacket(this, playerConnection);
            readPacket(socket);
        } catch (Exception e) {
            Main.getLogger().log(Level.SEVERE, e.getMessage(), e);
            disconnect(playerConnection, null);
        }
    }

    public void sendPacket(PlayerConnection playerConnection, PacketOut packetOut) {
        try {
            int id = playerConnection.getProtocol().getPacketOutByClass(packetOut.getClass(), playerConnection.getStatus());
            if (id == -1) {
                Main.getLogger().warning("Sending unknown packet " + packetOut.getClass().getSimpleName());
                return;
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PacketSerializer packetSerializer = new PacketSerializer(stream);
            packetSerializer.writeVarInt(id);
            packetOut.write(packetSerializer);

            byte[] data = stream.toByteArray();
            stream.close();
            stream = new ByteArrayOutputStream();
            packetSerializer = new PacketSerializer(stream);
            packetSerializer.writeByteArray(data);
            playerConnection.getSocket().write(ByteBuffer.wrap(stream.toByteArray()));
            stream.close();
        } catch (ClosedChannelException ignored) {
        } catch (Exception e) {
            Main.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void stop() {
        new ArrayList<>(playerConnections).forEach(playerConnection -> disconnect(playerConnection, "Server closed"));
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            Main.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public AbstractProtocol getProtocolByVersionId(int version) {
        for (AbstractProtocol protocol : this.protocols) {
            for (int id : protocol.getVersions()) {
                if (id == version) {
                    return protocol;
                }
            }
        }
        return null;
    }

    public int getCurrentKeepAliveId() {
        return keepAliveId;
    }

    public void disconnect(PlayerConnection playerConnection, String message) {
        if (playerConnection.getStatus() != Status.STATUS && playerConnection.getStatus() != Status.HANDSHAKE) {
            Main.getLogger().info(playerConnection.getInetAddress().getHostString() + (playerConnection.getName().isEmpty() ? "" : " (" + playerConnection.getName() + ")") + " disconnected" + (message == null ? "" : " : " + message));
        }
        playerConnections.remove(playerConnection);
        if (message != null) {
            playerConnection.getProtocol().disconnect(playerConnection, message);
        }
        try {
            playerConnection.getSocket().close();
        } catch (IOException e) {
            Main.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public World getWorld() {
        return world;
    }

    public Configuration getLimboConfiguration() {
        return limboConfiguration;
    }

    public long getConnectedPlayers() {
        return playerConnections.stream().filter(playerConnection -> playerConnection.getStatus() == Status.LOGIN || playerConnection.getStatus() == Status.PLAY).count();
    }
}
