package me.drmayx.voxelclearwand.voxelverseclearwand.listeners;

import me.drmayx.voxelclearwand.voxelverseclearwand.utils.TextUtils;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class ClearWandListener implements Listener {

    @EventHandler
    public void onWandUsed(PlayerInteractEvent event){

        Player player = event.getPlayer();

        try {
            if(event.getClickedBlock() != null &&
               player.getInventory().getItemInMainHand().hasItemMeta() &&
               player.getInventory().getItemInMainHand().getItemMeta().hasLore() &&
               player.getInventory().getItemInMainHand().getItemMeta().getLore().size() > 0 &&
               player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0) != null &&
               player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).equals(TextUtils.getLore0Message())) {

                event.setCancelled(true);

                if (event.getClickedBlock().getState() instanceof Container) {
                    if(!player.hasPermission("clearwand.use")) {
                        player.sendMessage(TextUtils.getInvalidUsePermissionsMessage());
                        return;
                    }

                    Inventory inventory = ((Container) event.getClickedBlock().getState()).getInventory();

                    inventory.clear();
                    player.sendMessage(TextUtils.getClearedMessage());
                }
            }
        }catch(Exception e){

            e.printStackTrace();
        }
    }
}
