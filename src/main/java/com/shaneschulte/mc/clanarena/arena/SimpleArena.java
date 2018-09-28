package com.shaneschulte.mc.clanarena.arena;

public class SimpleArena {

    private Region arenaRegion;

    public SimpleArena(Region arenaRegion) {
        this.arenaRegion = arenaRegion;
    }

    public Region getRegion() {
        return arenaRegion;
    }
}
