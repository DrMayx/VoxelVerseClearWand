package me.drmayx.voxelclearwand.voxelverseclearwand.utils;

import org.bukkit.ChatColor;

public class TextUtils {

    public static String getHelp(){
        return ChatColor.translateAlternateColorCodes('&', "&7Use /&etwand get &7to get a &e&lTrash Wand&7.&r");
    }

    public static String getLore0Message(){
        return ChatColor.translateAlternateColorCodes('&', "&7Right click on a chest&r");
    }

    public static String getLore1Message(){
        return ChatColor.translateAlternateColorCodes('&', "&7to clear its contents.&r");
    }

    public static String getClearedMessage(){
        return ChatColor.translateAlternateColorCodes('&', "&aContainer cleared.&r");
    }

    public static String getWandName(){
        return ChatColor.translateAlternateColorCodes('&', "&e&lTrash Wand&r");
    }

    public static String getNonPlayerText(){
        return ChatColor.translateAlternateColorCodes('&', "&4Only Player can use that command.");
    }

    public static String getInvalidGetPermissionsMessage(){
        return ChatColor.translateAlternateColorCodes('&', "&4Insufficient permissions to get Trash Wand.");
    }

    public static String getInvalidUsePermissionsMessage(){
        return ChatColor.translateAlternateColorCodes('&', "&4Insufficient permissions to use Trash Wand.");
    }
}
