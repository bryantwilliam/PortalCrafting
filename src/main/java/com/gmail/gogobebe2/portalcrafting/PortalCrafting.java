package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PortalCrafting extends JavaPlugin {
    private List<Portal> portals = new ArrayList<>();
    public static final ItemStack EXIT_PORTAL_ITEM = PortalType.EXIT.getItem();
    public static final ItemStack ENTRY_PORTAL_ITEM = PortalType.ENTRY.getItem();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
    }
}
