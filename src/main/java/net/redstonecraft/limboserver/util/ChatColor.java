package net.redstonecraft.limboserver.util;

import java.util.ArrayList;
import java.util.List;

public enum ChatColor {

    BLACK('0', "black"),
    DARK_BLUE('1', "dark_blue"),
    DARK_GREEN('2', "dark_green"),
    DARK_AQUA('3', "dark_aqua"),
    DARK_RED('5', "dark_purple"),
    GOLD('6', "gold"),
    GRAY('7', "gray"),
    DARK_GRAY('8', "dark_gray"),
    BLUE('9', "blue"),
    GREEN('a', "green"),
    AQUA('b', "aqua"),
    RED('c', "red"),
    LIGHT_PURPLE('d', "light_purple"),
    YELLOW('e', "yellow"),
    WHITE('f', "white"),
    MAGIC('k', "obfuscated"),
    BOLD('l', "bold"),
    STRIKETHROUGH('m', "strikethrough"),
    UNDERLINE('n', "underline"),
    ITALIC('o', "italic"),
    RESET('r', "reset");

    private static final char COLOR_CHAR = '\u00A7';
    private static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";

    public final char code;
    public final String name;
    
    ChatColor(char code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ChatColor getByCode(char code) {
        for (ChatColor i : values()) {
            if (i.code == code) {
                return i;
            }
        }
        return null;
    }

    public static String renderColors(String text) {
        char[] b = text.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '&' && ALL_CODES.indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static String colorToJson(String text) {
        return "{\"text\":\"" + text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\"}";
    }

}
