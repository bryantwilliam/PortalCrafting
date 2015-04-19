package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PortalCrafting extends JavaPlugin {
    private List<Portal> portals = new ArrayList<>();

    @Override
    public void onEnable() {
        ItemStack portal = new ItemStack(Material.ENDER_PORTAL_FRAME);
        setName(portal, ChatColor.BLUE + "Entry Portal");
        setRecipe(portal, PortalType.ENTRY);
    }

    private void setRecipe(ItemStack item, PortalType type) {
        ShapedRecipe recipe;
        final Material[] PLATE_TYPES = {Material.GOLD_PLATE, Material.IRON_PLATE, Material.STONE_PLATE, Material.WOOD_PLATE};
        for (Material plateType : PLATE_TYPES) {
            recipe = new ShapedRecipe(item);

            recipe.shape("ABA", "ACA", "AAA");
            recipe.setIngredient('A', Material.DIAMOND_BLOCK);
            recipe.setIngredient('B', plateType);
            recipe.setIngredient('C', type.equals(PortalType.ENTRY) ? Material.DROPPER : Material.DISPENSER);

            Bukkit.getServer().addRecipe(recipe);
        }
    }

    public static ItemStack setName(ItemStack stack, String name){
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }
}
