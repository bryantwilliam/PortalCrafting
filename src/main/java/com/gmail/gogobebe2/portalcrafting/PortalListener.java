package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PortalListener implements Listener {
    private PortalCrafting plugin;
    private Map<Player, Portal> selectedPortals = new HashMap<>();

    public PortalListener(PortalCrafting plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPortalPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Block blockPlaced = event.getBlockPlaced();
        if (PortalType.isItemPortal(item)) {
            for (PortalType type : PortalType.values()) {
                if (type.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemMeta().getDisplayName())) {
                    plugin.placePortal(new Portal(type, blockPlaced));
                    event.getPlayer().sendMessage(type.getDisplayName() + ChatColor.GREEN + " placed!");
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPortalDestroy(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getBlock();
        Portal portal = PortalCrafting.getPortal(block);
        if (portal != null) {
            Player player = event.getPlayer();
            plugin.removePortal(portal);
            event.getPlayer().sendMessage(portal.getType().getDisplayName() + ChatColor.GOLD + " removed!");
            if (portal.isLinked()) {
                Portal partner = portal.getPartner();
                Portal.breakLink(portal, partner);
                player.sendMessage(ChatColor.GOLD + "Link between " + portal.getType().getDisplayName()
                        + ChatColor.GOLD + " and " + partner.getType().getDisplayName() + " destroyed.");
            }
            player.getWorld().dropItemNaturally(block.getLocation(), portal.getType().getItem());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockRightClickPortal(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            Portal portal = PortalCrafting.getPortal(block);
            if (portal != null) {
                if (player.isSneaking()) {
                    player.sendMessage(ChatColor.GOLD + "Keep in mind, you're sneaking so no selection will take place.");

                } else {
                    Portal oppositePortal = selectedPortals.get(player);
                    if (selectedPortals.containsKey(player) && portal.getType().equals(oppositePortal.getType().getOpposite())) {
                        player.sendMessage(ChatColor.AQUA + "Linked " + portal.getType().getDisplayName() + ChatColor.AQUA
                                + " and " + oppositePortal.getType().getDisplayName());
                        Portal.createLink(portal, oppositePortal, plugin);
                        selectedPortals.remove(player);
                    } else {
                        selectedPortals.put(player, portal);
                        player.sendMessage(ChatColor.GREEN + "Selected " + portal.getType().getDisplayName());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerMinePortal(BlockDamageEvent event) {
        if (PortalCrafting.getPortal(event.getBlock()) != null) {
            event.setInstaBreak(true);
        }
    }

    private boolean isInRange(double num1, double num2, double min, double max) {
        return (num1 >= min && num1 <= max) && (num2 >= min && num2 <= max);
    }

    private boolean isInRange(Location location1, Location location2) {
        double x1 = location1.getX();
        double y1 = location1.getY();
        double z1 = location1.getZ();

        double x2 = location2.getX();
        double y2 = location2.getY();
        double z2 = location2.getZ();

        return (isInRange(x1, x2, x1 - 1, x1 + 1) && isInRange(y1, y2, y1 - 1, y1 + 1) && isInRange(z1, z2, z1 - 1, z1 + 1));
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerStepOnPortal(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();
        Block toBlock = to.getBlock();
        Block fromBlock = from.getBlock();

        if (isInRange(toBlock.getLocation(), fromBlock.getLocation())) {
            double eyeToBlockDifference = 0.8125;
            to.setY(to.getY() -eyeToBlockDifference);
            Block blockUnderneath = to.getBlock();
            Portal portal = PortalCrafting.getPortal(blockUnderneath);
            if (portal != null) {
                Player player = event.getPlayer();
                if (portal.isLinked()) {
                    if (portal.getType().equals(PortalType.ENTRY)) {
                        Portal partner = portal.getPartner();
                        Location destination = partner.getBlock().getLocation();
                        destination.setY(destination.getY() +eyeToBlockDifference);
                        player.teleport(destination);
                        player.sendMessage(ChatColor.DARK_AQUA + "Whoosh!");
                        player.playSound(destination, Sound.PORTAL_TRAVEL, 0.5F, 0.5F);
                    } else {
                        Location under = new Location(fromBlock.getWorld(), fromBlock.getX(), fromBlock.getY() -eyeToBlockDifference, fromBlock.getZ());
                        Block underBlock = under.getBlock();
                        if (PortalCrafting.getPortal(underBlock) == null) {
                            player.sendMessage(ChatColor.RED + "You can only teleport through entry portals. 1 way only. "
                                    + "New recipe for multiway portals coming soon! " +
                                    "Bug me at gogobebe2@gmail.com to make me do it.");
                        }
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "You cannot use this portal yet! It's hasn't linked yet!");
                }
            }
        }
    }
}
