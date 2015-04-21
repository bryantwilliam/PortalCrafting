package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.block.Block;

public class Portal {
    private PortalType type;
    private Block block;
    private Portal partner = null;

    public Portal(PortalType type, Block block) {
        this.type = type;
        this.block = block;
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
