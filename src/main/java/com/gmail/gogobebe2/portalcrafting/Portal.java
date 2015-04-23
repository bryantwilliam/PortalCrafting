package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.block.Block;

public class Portal {
    private PortalType type;
    private Block block;
    private Portal partner = null;
    private int ID;

    public Portal(PortalType type, Block block) {
        this(type, block, PortalCrafting.getPortals().size() + 1);
    }

    public Portal(PortalType type, Block block, int ID) {
        this.type = type;
        this.block = block;
        this.ID = ID;
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

    public static void createLink(Portal portal, Portal oppositePortal, PortalCrafting plugin) {
        portal.createLink(oppositePortal, plugin);
        oppositePortal.createLink(portal, plugin);
    }

    private void createLink(Portal partner, PortalCrafting plugin) {
        if (isLinked()) {
            breakLink(this, this.partner);
        }
        this.partner = partner;
        plugin.getConfig().set("portals." + this.getID() + ".partnerID", partner.getID());
        plugin.saveConfig();
    }

    public static void breakLink(Portal portal, Portal partner) {
        portal.breakLink();
        partner.breakLink();
    }

    private void breakLink() {
        partner = null;
    }

    public boolean isLinked() {
        return (partner != null);
    }

    public Portal getPartner() {
        return this.partner;
    }
}
