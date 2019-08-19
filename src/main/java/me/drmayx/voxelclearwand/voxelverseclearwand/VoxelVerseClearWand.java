package me.drmayx.voxelclearwand.voxelverseclearwand;

import me.drmayx.voxelclearwand.voxelverseclearwand.commands.ClearWandCommand;
import me.drmayx.voxelclearwand.voxelverseclearwand.listeners.ClearWandListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class VoxelVerseClearWand extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("clearwand").setExecutor(new ClearWandCommand());
        getServer().getPluginManager().registerEvents(new ClearWandListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
