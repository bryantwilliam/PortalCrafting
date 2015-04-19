package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalCrafting extends JavaPlugin {
    public final static ItemStack ENTER_PORTAL = setName(new ItemStack(Material.ENDER_PORTAL), ChatColor.BLUE + "Enter Portal");
    public final static ItemStack EXIT_PORTAL = setName(new ItemStack(Material.ENDER_PORTAL), ChatColor.RED + "Exit Portal");

    @Override
    public void onEnable() {
        createRecipies();
    }

    private void createRecipies() {
        final Material[] plateTypes = {Material.GOLD_PLATE, Material.IRON_PLATE, Material.STONE_PLATE, Material.WOOD_PLATE};
        for (Material plate : plateTypes) {
            ShapedRecipe exitRecipe = new ShapedRecipe(EXIT_PORTAL);
            ShapedRecipe enterRecipe = new ShapedRecipe(ENTER_PORTAL);

            enterRecipe.shape("ABA", "ACA", "AAA");
            enterRecipe.setIngredient('A', Material.DIAMOND_BLOCK);
            enterRecipe.setIngredient('B', plate);
            enterRecipe.setIngredient('C', Material.DROPPER);

            exitRecipe.shape("ABA", "ACA", "AAA");
            exitRecipe.setIngredient('A', Material.DIAMOND_BLOCK);
            exitRecipe.setIngredient('B', plate);
            exitRecipe.setIngredient('C', Material.DISPENSER);

            Bukkit.getServer().addRecipe(exitRecipe);
            Bukkit.getServer().addRecipe(enterRecipe);
        }

    }
    public static ItemStack setName(ItemStack is, String name){
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
        return is;
    }
}
