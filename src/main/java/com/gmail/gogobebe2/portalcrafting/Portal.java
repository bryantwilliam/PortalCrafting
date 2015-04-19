package com.gmail.gogobebe2.portalcrafting;

import org.bukkit.Location;

public class Portal {
    private PortalType type;
    private Location location;
    private boolean isLinked;
    private Portal partner = null;

    public Portal(PortalType type, Location location) {
        this.type = type;
        this.location = location;
    }
}
