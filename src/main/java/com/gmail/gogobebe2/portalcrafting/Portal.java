package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.block.Block;

public class Portal {
    private PortalType type;
    private Block block;
    private boolean isLinked;
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
}
