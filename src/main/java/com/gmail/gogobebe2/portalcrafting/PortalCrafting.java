package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PortalCrafting extends JavaPlugin {
    private static List<Portal> portals = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PortalListener(this), this);
        PortalType.initializeRecipes();
    }

    public static List<Portal> getPortals() {
        return portals;
    }

    public static Portal getPortal(Block block) {
        for (Portal portal : portals) {
            if (portal.getBlock().equals(block)) {
                return portal;
            }
        }
        return null;
    }

    public static void placePortal(Portal portal) {
        PortalCrafting.getPortals().add(portal);
    }
}
