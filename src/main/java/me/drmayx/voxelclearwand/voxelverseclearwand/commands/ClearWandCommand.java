package me.drmayx.voxelclearwand.voxelverseclearwand.commands;

import me.drmayx.voxelclearwand.voxelverseclearwand.utils.ItemCreator;
import me.drmayx.voxelclearwand.voxelverseclearwand.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearWandCommand implements CommandExecutor {

    private ItemCreator creator;

    public ClearWandCommand(){
        this.creator = new ItemCreator();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                switch (args[0]) {
                    case "get":
                        if(!player.hasPermission("clearwand.get")) {
                            player.sendMessage(TextUtils.getInvalidGetPermissionsMessage());
                            return true;
                        }
                        handleGive(player);
                        break;
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
        player.getInventory().addItem(this.creator.createNewWand());
    }
}
