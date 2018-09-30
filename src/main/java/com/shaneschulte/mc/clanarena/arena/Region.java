package com.shaneschulte.mc.clanarena.arena;

import org.bukkit.Location;

public class Region {

    private Location pos1, pos2;

    public Region(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public boolean inArea(Location targetLocation) {
        return inArea(targetLocation, true);
    }

    public boolean inArea(Location targetLocation, boolean checkY) {
        if(pos1.getWorld().getName().equals(pos2.getWorld().getName())) { // Check for worldName location1, location2
            if(targetLocation.getWorld().getName().equals(pos1.getWorld().getName())) { // Check for worldName targetLocation, location1
                if((targetLocation.getBlockX() >= pos1.getBlockX() && targetLocation.getBlockX() <= pos2.getBlockX()) || (targetLocation.getBlockX() <= pos1.getBlockX() && targetLocation.getBlockX() >= pos2.getBlockX())){ // Check X value
                    if((targetLocation.getBlockZ() >= pos1.getBlockZ() && targetLocation.getBlockZ() <= pos2.getBlockZ()) || (targetLocation.getBlockZ() <= pos1.getBlockZ() && targetLocation.getBlockZ() >= pos2.getBlockZ())){ // Check Z value
                        if (checkY) { // If should check for Y value
                            if ((targetLocation.getBlockY() >= pos1.getBlockY() && targetLocation.getBlockY() <= pos2.getBlockY()) || (targetLocation.getBlockY() <= pos1.getBlockY() && targetLocation.getBlockY() >= pos2.getBlockY())){ // Check Y value
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}