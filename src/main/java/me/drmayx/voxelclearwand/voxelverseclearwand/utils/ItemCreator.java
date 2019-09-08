package me.drmayx.voxelclearwand.voxelverseclearwand.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {

    public ItemStack createNewWand(int durability){
        ItemStack wand = new ItemStack(Material.GOLDEN_AXE);

        ItemMeta wandMeta = wand.getItemMeta();

        List<String> lore = new ArrayList<>();

        lore.add(TextUtils.getLore0Message());                              // 0
        lore.add(TextUtils.getLore1Message());                              // 1
        lore.add("");                                                       // 2
        lore.add(String.format(TextUtils.getLore2Message(), durability));   // 3

        wandMeta.setDisplayName(TextUtils.getWandName());

        wandMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
        wandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wandMeta.setLore(lore);
        wand.setItemMeta(wandMeta);

        return wand;
    }
}
