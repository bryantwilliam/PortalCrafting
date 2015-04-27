package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.block.Block;

public class Portal {
    private PortalCrafting plugin;
    private PortalType type;
    private Block block;
    private Portal partner = null;
    private int ID;

    public Portal(PortalType type, Block block, PortalCrafting plugin) {
        this(type, block, plugin, PortalCrafting.getPortals().size() + 1);
    }

    public Portal(PortalType type, Block block, PortalCrafting plugin, int ID) {
        this.type = type;
        this.block = block;
        this.ID = ID;
        this.plugin = plugin;
    }

    public int getID() {
        return this.ID;
    }

    public Block getBlock() {
        return this.block;
    }

    public PortalType getType() {
        return this.type;
    }

    public static void createLink(Portal portal, Portal oppositePortal) {
        portal.createLink(oppositePortal);
        oppositePortal.createLink(portal);
    }

    private void createLink(Portal partner) {
        this.partner = partner;
        if (isLinked()) {
            breakLink(this, this.partner);
        }
        plugin.getConfig().set("portals." + this.getID() + ".partnerID", partner.getID());
        plugin.saveConfig();
    }

    public static void breakLink(Portal portal, Portal partner) {
        portal.breakLink();
        partner.breakLink();
    }

    private void breakLink() {
        partner = null;
        plugin.getConfig().set("portals." + this.getID() + ".partnerID", null);
        plugin.saveConfig();
    }

    public boolean isLinked() {
        return (partner != null);
    }

    public Portal getPartner() {
        return this.partner;
    }
}
