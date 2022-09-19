package net.kumc.lab.commandblocklogger;


import org.bukkit.Location;

public class PlacerData {
    private String placer;
    private Location location;

    public PlacerData(String placer, Location location) {
        this.placer = placer;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public String getPlacer() {
        return placer;
    }
}
