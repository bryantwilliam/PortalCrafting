package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PortalListener implements Listener {
    PortalCrafting plugin;

    public PortalListener(PortalCrafting plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Block blockPlaced = event.getBlockPlaced();
        if (item.equals(PortalType.ENTRY.getItem())) {
            plugin.getPortals().add(new Portal(PortalType.ENTRY, blockPlaced));
            event.getPlayer().sendMessage(PortalType.ENTRY.getDisplayName() + ChatColor.GREEN + " placed!");
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        Block blockDestroyed = event.getBlock();
        for (Portal portal : plugin.getPortals()) {
            if (portal.getBlock().equals(blockDestroyed)) {
                plugin.getPortals().remove(portal);
                event.getPlayer().sendMessage(portal.getType().getDisplayName() + ChatColor.GOLD + " removed!");
                break;
            }
        }
    }
}
