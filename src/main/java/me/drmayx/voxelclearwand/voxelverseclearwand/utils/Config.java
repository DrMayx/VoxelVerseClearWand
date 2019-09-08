package me.drmayx.voxelclearwand.voxelverseclearwand.utils;

        import org.bukkit.configuration.file.YamlConfiguration;
        import org.bukkit.plugin.java.JavaPlugin;

public class Config {

    public static YamlConfiguration generateConfig(JavaPlugin plugin){
        YamlConfiguration config = (YamlConfiguration) plugin.getConfig();
        config.options().header("All settings require server restart.\n" +
                "clear_wand_interval - enter the amount of time in milliseconds that has to pass until user can gat new clearwand;\n" +
                "clear_wand_usage_limit - the amount of times player can use trash wand until it breaks.");

        config.set("clear_wand_interval", 10000);
        config.set("clear_wand_usage_limit", 32);

        plugin.saveConfig();
        return (YamlConfiguration) plugin.getConfig();
    }
}
