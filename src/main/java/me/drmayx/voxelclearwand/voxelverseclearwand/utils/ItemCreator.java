package me.drmayx.voxelclearwand.voxelverseclearwand.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {

    public ItemStack createNewWand(){
        ItemStack wand = new ItemStack(Material.GOLDEN_AXE);

        ItemMeta wandMeta = wand.getItemMeta();

        List<String> lore = new ArrayList<>();

        lore.add(TextUtils.getLore0Message());
        lore.add(TextUtils.getLore1Message());

        wandMeta.setDisplayName(TextUtils.getWandName());

        wandMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        wandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wandMeta.setLore(lore);
        wand.setItemMeta(wandMeta);

        return wand;
    }
}
