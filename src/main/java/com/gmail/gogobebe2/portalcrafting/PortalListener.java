package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PortalListener implements Listener {
    private PortalCrafting plugin;
    private Map<Player, Portal> selectedPortals = new HashMap<>();

    public PortalListener(PortalCrafting plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Block blockPlaced = event.getBlockPlaced();
        if (PortalType.isItemPortal(item)) {
            for (PortalType type : PortalType.values()) {
                if (type.getItem().equals(item)) {
                    PortalCrafting.placePortal(new Portal(type, blockPlaced));
                    event.getPlayer().sendMessage(type.getDisplayName() + ChatColor.GREEN + " placed!");
                }
            }
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        Block block = event.getBlock();
        Portal portal = PortalCrafting.getPortal(block);
        if (portal != null) {
            PortalCrafting.getPortals().remove(portal);
            event.getPlayer().sendMessage(portal.getType().getDisplayName() + ChatColor.GOLD + " removed!");
        }
    }

    public void onBlockRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            Portal portal = PortalCrafting.getPortal(block);
            if (portal != null) {
                if (selectedPortals.containsKey(player) && portal.getType().equals(selectedPortals.get(player).getType().getOpposite())) {
                    // TODO: Create link.
                }
                else {
                    selectedPortals.put(player, portal);
                    player.sendMessage(ChatColor.GREEN + "Selected " + portal.getType().getDisplayName());
                }
            }
        }
    }
}
