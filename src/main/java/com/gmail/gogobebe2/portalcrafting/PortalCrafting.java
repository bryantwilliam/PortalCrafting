package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PortalCrafting extends JavaPlugin {
    private static List<Portal> portals = new ArrayList<>();
    private MySQLManager mysql = new MySQLManager(this);
    private boolean useMySQL;

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        if (useMySQL) {
            // TODO: add sql shit here.
            try {
                mysql.setupDB();
            } catch (SQLException e) {
                e.printStackTrace();
                getLogger().severe("Error! Could not connect to database!");
            }
        }
        else {
            loadPortalsFromConfig();
        }

        useMySQL = getConfig().getBoolean("useMySQL");

        Bukkit.getPluginManager().registerEvents(new PortalListener(this), this);

        PortalType.initializeRecipes();
    }

    public void onDisable() {
        reloadConfig();
        saveConfig();
        if (useMySQL) {
            try {
                mysql.closeDB();
            } catch (SQLException e) {
                e.printStackTrace();
                getLogger().severe("Error! The database has closed before I could!");
            }
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("portalcrafting")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    if (useMySQL) {
                        // TODO: do sql stuff.
                    }
                    sender.sendMessage(ChatColor.GREEN + "config/databases reloaded!");
                }
            }
            return true;
        }
        return false;
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

    public static Portal getPortal(int ID) {
        for (Portal portal : portals) {
            if (portal.getID() == ID) {
                return portal;
            }
        }
        return null;
    }

    public void removePortal(Portal portal) {
        PortalCrafting.getPortals().remove(portal);
        if (useMySQL) {
            // TODO: add sql shit here.
        } else {
            getConfig().set("portals." + portal.getID(), null);
            saveConfig();
        }

    }

    public void placePortal(Portal portal) {
        PortalCrafting.getPortals().add(portal);
        Location location = portal.getBlock().getLocation();
        double X = location.getX();
        double Y = location.getY();
        double Z = location.getZ();
        String world = location.getWorld().getName();

        if (useMySQL) {
            // TODO: add sql shit here.
        } else {
            getConfig().set("portals." + portal.getID() + ".type", portal.getType().name());
            getConfig().set("portals." + portal.getID() + ".location.x", X);
            getConfig().set("portals." + portal.getID() + ".location.y", Y);
            getConfig().set("portals." + portal.getID() + ".location.z", Z);
            getConfig().set("portals." + portal.getID() + ".location.world", world);
            try {
                getConfig().set("portals." + portal.getID() + ".partnerID", portal.getPartner().getID());
            }
            catch (NullPointerException ex) {
                // No partner so don't need to add it's ID.
            }

            saveConfig();
        }
    }

    private void loadPortalsFromConfig() {
        if (getConfig().isSet("portals")) {
            for (String key : getConfig().getConfigurationSection("portals").getKeys(false)) {
                int ID = Integer.parseInt(key);
                Portal portal = getPortalFromConfig(ID);
                portals.add(portal);
            }
            if (!portals.isEmpty()) {
                for (Portal portal : portals) {
                    int ID = portal.getID();
                    if (getConfig().isSet("portals." + ID + ".partnerID")) {
                        Portal partner = getPortal(getConfig().getInt("portals." + ID + ".partnerID"));
                        if (partner != null) {
                            Portal.createLink(portal, partner);
                        }
                    }
                }
            }
        }
    }

    private Portal getPortalFromConfig(int ID) {
        PortalType type = PortalType.valueOf(getConfig().getString("portals." + ID + ".type"));
        Location location = new Location(Bukkit.getWorld(
                getConfig().getString("portals." + ID + ".location.world")),
                getConfig().getDouble("portals." + ID + ".location.x"),
                getConfig().getDouble("portals." + ID + ".location.y"),
                getConfig().getDouble("portals." + ID + ".location.z"));
        Block block = location.getBlock();
        return new Portal(type, block, this, ID);
    }

}
