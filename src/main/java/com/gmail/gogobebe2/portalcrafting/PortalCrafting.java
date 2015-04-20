package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PortalCrafting extends JavaPlugin {
    private List<Portal> portals = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PortalListener(this), this);
        PortalType.initializeRecipes();
    }

    public List<Portal> getPortals() {
        return portals;
    }
}
