package net.redstonecraft.limboserver;

import net.redstonecraft.limboserver.world.Schematic;
import net.redstonecraft.limboserver.world.World;
import net.redstonecraft.limboserver.world.nbt.NBTTag;

import java.io.*;
import java.util.Date;
import java.util.logging.*;
import java.util.zip.GZIPInputStream;

public class Main {

    private static final Logger logger = Logger.getLogger("LimboServer");
    private final NetworkManager networkManager;
    public boolean run = true;
    private static Configuration config;

    public static void main(String[] args) throws Exception {
        config = Configuration.load();
        Main limbo = new Main(config);
        Runtime.getRuntime().addShutdownHook(new Thread(limbo::stop));
    }

    private Main(Configuration config) throws IOException {
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            private static final String format = "[%1$tT] [%3$s] %4$s : %5$s%6$s%n";

            @Override
            public String format(LogRecord record) {
                Date date = new Date(record.getMillis());
                String source;
                if (record.getSourceClassName() != null) {
                    source = record.getSourceClassName();
                    if (record.getSourceMethodName() != null) {
                        source += " " + record.getSourceMethodName();
                    }
                } else {
                    source = record.getLoggerName();
                }
                String message = formatMessage(record);
                String throwable = "";
                if (record.getThrown() != null) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    pw.println();
                    record.getThrown().printStackTrace(pw);
                    pw.close();
                    throwable = sw.toString();
                }
                return String.format(format, date, source, record.getLoggerName(), record.getLevel().getLocalizedName(), message, throwable);
            }
        });
        logger.addHandler(handler);
        logger.info("Loading schematic from " + config.getSchematicFile().getName());
        FileInputStream fileInputStream = new FileInputStream(config.getSchematicFile());
        World world = Schematic.toWorld(NBTTag.readTag(new GZIPInputStream(fileInputStream)).toCompoundTag());
        logger.info("World loaded");
        networkManager = new NetworkManager(world, config);
        networkManager.bind(config.getIp(), config.getPort());
        logger.info("Listening on port " + config.getPort());
        while (run) {
            this.networkManager.tick();
        }
    }

    private void stop() {
        run = false;
        networkManager.stop();
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Configuration getConfig() {
        return config;
    }

}
