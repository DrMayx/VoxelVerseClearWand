package me.drmayx.voxelclearwand.voxelverseclearwand.commands;

import me.drmayx.voxelclearwand.voxelverseclearwand.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Date;
import java.util.Random;

public class ClearWandCommand implements CommandExecutor {

    private ItemCreator creator;
    private YamlConfiguration config = null;
    private long getWandInterval;
    private int wandDurability;

    public ClearWandCommand(){

        this.creator = new ItemCreator();

        // Config section

        File configFile = new File(FileHandler.CONFIG_PATH);

        if(!configFile.exists())
        {
            this.config = Config.generateConfig((JavaPlugin) Bukkit.getPluginManager().getPlugin("VoxelVerseClearWand"));
        }
        else{
            this.config = YamlConfiguration.loadConfiguration(configFile);
        }

        this.getWandInterval = config.getLong("clear_wand_interval");
        this.wandDurability = config.getInt("clear_wand_usage_limit");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length > 0 && args[0].equalsIgnoreCase("reload")){
            if(sender.hasPermission("clearwand.admin")) {
                Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("VoxelVerseClearWand"));
                Bukkit.getServer().getPluginManager().enablePlugin(Bukkit.getPluginManager().getPlugin("VoxelVerseClearWand"));
            }else{
                sender.sendMessage(TextUtils.getInvalidReloadPermissionsMessage());
            }
            return true;
        }

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if(args[0].equalsIgnoreCase("get")){
                    if(!player.hasPermission("clearwand.get")) {
                        player.sendMessage(TextUtils.getInvalidGetPermissionsMessage());
                        return true;
                    }
                    handleGive(player);
                }
                return true;
            } else {
                player.sendMessage(TextUtils.getHelp());
                return true;
            }
        }else {
            sender.sendMessage(TextUtils.getNonPlayerText());
            return true;
        }
    }

    private void handleGive(Player player){

        long playerTimer = getDateFor(player).getTime();
        Date now = new Date();
        long result = now.getTime() - playerTimer;

        if(result > this.getWandInterval){
            if(FileHandler.saveToDatabase(new PlayerRecord(player.getUniqueId(), new Date()), player)) {
                player.getInventory().addItem(this.creator.createNewWand(this.wandDurability));
            }else{
                player.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                        (new Random().nextLong()) +
                        "FSBrk");
                System.out.println("Some issue with saving. Contact DrMayX");
            }

        }else{
            player.sendMessage(ChatColor.RED + "You can't get new clearwand for another " + ((this.getWandInterval - result)/1000) + " seconds" + ChatColor.RESET);
        }
    }

    private Date getDateFor(Player player){
        PlayerRecord record = FileHandler.readFromDatabase(player);

        if(record == null){
            return new Date(0);
        }
        return record.timestamp;
    }
}
