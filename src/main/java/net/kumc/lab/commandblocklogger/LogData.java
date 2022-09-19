package net.kumc.lab.commandblocklogger;

import org.bukkit.Location;

public class LogData {

    private int id;
    private String command;
    private Location location;
    private String person;

    /**
     * @param id       ID
     * @param person   Date and time of execution
     * @param command  Command String
     * @param location Command String
     */
    public LogData(int id, String person, String command, Location location) {
        this.id = id;
        this.person = person;
        this.command = command;
        this.location = location;
    }


    public int getId() {
        return id;
    }

    public String getPerson() {
        return person;
    }

    public String getCommand() {
        return command;
    }

    public Location getLocation() {
        return location;
    }

}
