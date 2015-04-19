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
        initializePortalItems();
    }

    private static void initializePortalItems() {
        createPortalItem(PortalType.ENTRY);
        createPortalItem(PortalType.EXIT);
    }
    private static void createPortalItem(PortalType type) {
        ItemStack portal = new ItemStack(Material.ENDER_PORTAL_FRAME);
        setMeta(portal, type.equals(PortalType.ENTRY) ? ChatColor.BLUE + "Entry Portal" : ChatColor.RED + "Exit Portal");
        setRecipe(portal, type);
    }

    private static void setRecipe(ItemStack item, PortalType type) {
        ShapedRecipe recipe;
        final Material[] PLATE_TYPES = {Material.GOLD_PLATE, Material.IRON_PLATE, Material.STONE_PLATE, Material.WOOD_PLATE};
        for (Material plateType : PLATE_TYPES) {
            recipe = new ShapedRecipe(item);

            recipe.shape("ABA", "ACA", "AAA");
            recipe.setIngredient('A', Material.DIAMOND_BLOCK);
            recipe.setIngredient('B', plateType);
            recipe.setIngredient('C', type.equals(PortalType.ENTRY) ? Material.HOPPER : Material.DISPENSER);

            Bukkit.getServer().addRecipe(recipe);
        }
    }

    private static void setMeta(ItemStack stack, String name) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = meta.getLore();
        lore.clear();
        lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Usage:");
        lore.add(ChatColor.DARK_AQUA + "1. "  + ChatColor.YELLOW + "Place down your entry portal.");
        lore.add(ChatColor.DARK_AQUA + "2. "  + ChatColor.YELLOW +  "Right click your entry portal.");
        lore.add(ChatColor.DARK_AQUA + "3. " + ChatColor.YELLOW + "Place down your exit portal.");
        lore.add(ChatColor.DARK_AQUA + "4. " + ChatColor.YELLOW + "Congratulations! You've created a link with your entry portal and exit portal. If you need more help, email me at gogobebe2@gmail.com");
        stack.setItemMeta(meta);
    }
}
