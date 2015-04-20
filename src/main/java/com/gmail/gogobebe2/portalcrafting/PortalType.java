package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum PortalType {
    EXIT, ENTRY;

    private ItemStack item;
    private String displayName;

    static {
        ENTRY.displayName = ChatColor.BLUE + "Entry Portal";
        EXIT.displayName = ChatColor.RED + "Exit Portal";
    }

    public static void initializeRecipes() {
        for (PortalType type : PortalType.values()) {
            type.item = type.createItem();
        }
    }

    public ItemStack getItem() {
        return item;
    }

    private ItemStack createItem() {
        ItemStack portal = new ItemStack(Material.ENDER_PORTAL_FRAME);
        setMeta(portal, this.getDisplayName());

        ShapedRecipe recipe;
        final Material[] PLATE_TYPES = {Material.GOLD_PLATE, Material.IRON_PLATE, Material.STONE_PLATE, Material.WOOD_PLATE};
        for (Material plateType : PLATE_TYPES) {
            recipe = new ShapedRecipe(portal);

            recipe.shape("ABA", "ACA", "AAA");
            recipe.setIngredient('A', Material.DIAMOND_BLOCK);
            recipe.setIngredient('B', plateType);
            recipe.setIngredient('C', this.getSpecialIngredient());

            Bukkit.getServer().addRecipe(recipe);
        }
        return portal;
    }

    private static void setMeta(ItemStack stack, String name) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Usage:");
        lore.add(ChatColor.DARK_AQUA + "1. "  + ChatColor.YELLOW + "Place down your entry portal.");
        lore.add(ChatColor.DARK_AQUA + "2. "  + ChatColor.YELLOW +  "Place down your exit portal.");
        lore.add(ChatColor.DARK_AQUA + "3. " + ChatColor.YELLOW + "Right click the entry portal.");
        lore.add(ChatColor.DARK_AQUA + "4. " + ChatColor.YELLOW + "Right click the exit portal.");
        lore.add(ChatColor.DARK_AQUA + "5. " + ChatColor.YELLOW + "You're done! Easy right?");
        lore.add(ChatColor.YELLOW + "   You've created a link with your ");
        lore.add(ChatColor.YELLOW + "   entry portal and exit portal.");
        lore.add(ChatColor.YELLOW + "   If you need more help, ");
        lore.add(ChatColor.YELLOW + "   email me at gogobebe2@gmail.com");
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    private Material getSpecialIngredient() {
        Material specialIngredient = null;
        switch (this) {
            case ENTRY:
                specialIngredient = Material.HOPPER;
                break;
            case EXIT:
                specialIngredient = Material.DISPENSER;
                break;
        }
        return specialIngredient;
    }
}

