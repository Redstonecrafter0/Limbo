package net.redstonecraft.limboserver;

import net.redstonecraft.limboserver.util.ChatColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Configuration {

    private int maxSlots;
    private short port;
    private String ip;
    private File schematicFile;
    private byte dimension;
    private byte gameMode;
    private double spawnX;
    private double spawnY;
    private double spawnZ;
    private float spawnYaw;
    private float spawnPitch;
    private boolean reducedDebugInfo;
    private String tabheader;
    private String tabfooter;
    private String tabheadernew;
    private String tabfooternew;
    private String chatformat;

    public static Configuration load() throws IOException {
        File file = new File("server.properties");
        if (!file.exists()) {
            Files.copy(Main.class.getResourceAsStream("/server.properties"), Paths.get(file.toURI()));
        }
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        Configuration limboConfiguration = new Configuration();
        limboConfiguration.maxSlots = Configuration.getInt(properties, "maxSlots", -1);
        limboConfiguration.port = (short) Configuration.getInt(properties, "server-port", 25565);
        limboConfiguration.ip = Configuration.getString(properties, "server-ip", "localhost");
        limboConfiguration.schematicFile = new File(Configuration.getString(properties, "schematic-file", "world.schematic"));
        limboConfiguration.dimension = (byte) Configuration.getInt(properties, "dimension", 1);
        limboConfiguration.gameMode = (byte) Configuration.getInt(properties, "gamemode", 2);
        limboConfiguration.reducedDebugInfo = Boolean.parseBoolean(Configuration.getString(properties, "reduceddebuginfo", "true"));
        limboConfiguration.tabheader = ChatColor.colorToJson(ChatColor.renderColors(Configuration.getString(properties, "tabheader", "")));
        limboConfiguration.tabfooter = ChatColor.colorToJson(ChatColor.renderColors(Configuration.getString(properties, "tabfooter", "")));
        limboConfiguration.chatformat = Configuration.getString(properties, "chatformat", "<%name%> %message%");
        String[] split = Configuration.getString(properties, "spawn", "0.5;64;0.5;0;0").split(";");
        try {
            limboConfiguration.spawnX = Double.parseDouble(split[0]);
            limboConfiguration.spawnY = Double.parseDouble(split[1]);
            limboConfiguration.spawnZ = Double.parseDouble(split[2]);
            limboConfiguration.spawnYaw = Float.parseFloat(split[3]);
            limboConfiguration.spawnPitch = Float.parseFloat(split[4]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidPropertiesFormatException("Invalid coordinate format");
        }
        return limboConfiguration;
    }

    private static int getInt(Properties properties, String key, int def) throws InvalidPropertiesFormatException {
        String result = properties.getProperty(key);
        if (result == null) {
            return def;
        }
        try {
            return Integer.parseInt(result);
        } catch (NumberFormatException e) {
            throw new InvalidPropertiesFormatException(key + " is not a number property");
        }
    }

    private static String getString(Properties properties, String key, String def) {
        String result = properties.getProperty(key);
        return result == null ? def : result;
    }

    public byte getDimension() {
        return dimension;
    }

    public byte getGameMode() {
        return gameMode;
    }

    public double getSpawnX() {
        return spawnX;
    }

    public double getSpawnY() {
        return spawnY;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public double getSpawnZ() {
        return spawnZ;
    }

    public short getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public File getSchematicFile() {
        return schematicFile;
    }

    public float getSpawnYaw() {
        return spawnYaw;
    }

    public float getSpawnPitch() {
        return spawnPitch;
    }

    public String getTabHeader() {
        return tabheader;
    }

    public String getTabFooter() {
        return tabfooter;
    }

    public String getTabHeaderNew() {
        return tabheadernew;
    }

    public String getTabFooterNew() {
        return tabfooternew;
    }

    public String formatChatMessage(PlayerConnection playerConnection, String message) {
        return ChatColor.renderColors(chatformat).replace("%name%", playerConnection.getName()).replace("%message%", message);
    }

    public String formatChatMessageNew(PlayerConnection playerConnection, String message) {
        return ChatColor.renderColors(chatformat).replace("%name%", playerConnection.getName()).replace("%message%", message);
    }

    public boolean isReducedDebugInfo() {
        return reducedDebugInfo;
    }
}
