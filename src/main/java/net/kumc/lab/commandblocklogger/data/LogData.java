package net.kumc.lab.commandblocklogger.data;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LogData {

    @Getter
    @CsvBindByPosition(position = 0)
    private int id;
    @Getter
    @CsvBindByPosition(position = 1)
    private String command;
    @CsvBindByPosition(position = 2)
    private String world;
    @CsvBindByPosition(position = 3)
    private double x;
    @CsvBindByPosition(position = 4)
    private double y;
    @CsvBindByPosition(position = 5)
    private double z;
    @Getter
    @CsvBindByPosition(position = 6)
    private String person;

    public LogData() {
    }

    /**
     * @param id
     * @param person
     * @param command
     * @param world   position
     * @param x       position
     * @param y       position
     * @param z       position
     */
    public LogData(int id, String person, String command, String world, double x, double y, double z) {
        this.id = id;
        this.person = person;
        this.command = command;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}
