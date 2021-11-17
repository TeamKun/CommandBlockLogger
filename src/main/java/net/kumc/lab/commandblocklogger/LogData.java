package net.kumc.lab.commandblocklogger;

import org.bukkit.Location;

public class LogData {

    private int id;
    private String command;
    private Location location;
    private String time;

    /**
     * @param id ID
     * @param time Date and time of execution
     * @param command Command String
     * @param location Command String
     */
    public LogData(int id , String time , String command , Location location){
        this.id = id;
        this.command = command;
        this.location = location;
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public String getTime(){
        return time;
    }

    public Location getLocation() {
        return location;
    }

    public String getCommand() {
        return command;
    }
}
