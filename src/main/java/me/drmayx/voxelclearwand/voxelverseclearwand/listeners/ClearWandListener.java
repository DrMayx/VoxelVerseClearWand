package me.drmayx.voxelclearwand.voxelverseclearwand.listeners;

import me.drmayx.voxelclearwand.voxelverseclearwand.utils.TextUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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
                    ItemStack item = player.getInventory().getItemInMainHand();
                    List<String> lore = item.getItemMeta().getLore();
                    String durabilityLore = lore.get(3);
                    // Parsing stuff
                    int durabilityStart = durabilityLore.indexOf(":") + 2;

                    String message = durabilityLore.substring(0, durabilityStart);
                    int durability = Integer.parseInt(durabilityLore.substring(durabilityStart));

                    // Actual durability

                    durability -= 1;

                    message += durability;
                    lore.set(3, message);

                    ItemMeta meta = item.getItemMeta();
                    meta.setLore(lore);

                    item.setItemMeta(meta);

                    if(durability == 0){
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6§lClearWand broke!"));
                    }
                    player.sendMessage(TextUtils.getClearedMessage());
                }
            }
        }catch(Exception e){

            e.printStackTrace();
        }
    }
}
