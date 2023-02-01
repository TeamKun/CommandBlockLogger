package net.kumc.lab.commandblocklogger.data;


import lombok.Getter;
import org.bukkit.Location;

public class PlacerData {
    @Getter
    private String placer;
    @Getter
    private Location location;

    public PlacerData(String placer, Location location) {
        this.placer = placer;
        this.location = location;
    }
}
