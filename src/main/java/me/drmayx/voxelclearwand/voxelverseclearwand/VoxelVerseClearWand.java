package me.drmayx.voxelclearwand.voxelverseclearwand;

import me.drmayx.voxelclearwand.voxelverseclearwand.commands.ClearWandCommand;
import me.drmayx.voxelclearwand.voxelverseclearwand.listeners.ClearWandListener;
import me.drmayx.voxelclearwand.voxelverseclearwand.utils.FileHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class VoxelVerseClearWand extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        try {
            FileHandler.Init();
        } catch (IOException e) {
            System.out.println("Error creating the database file.");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("clearwand").setExecutor(new ClearWandCommand());
        getServer().getPluginManager().registerEvents(new ClearWandListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
