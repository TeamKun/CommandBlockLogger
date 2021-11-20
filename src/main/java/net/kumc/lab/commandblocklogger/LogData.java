package net.kumc.lab.commandblocklogger;

import org.bukkit.Location;

public class LogData {

    private int id;
    private String command;
    private Location location;
    private String person;

    /**
     * @param id ID
     * @param person Date and time of execution
     * @param command Command String
     * @param location Command String
     */
    public LogData(int id , String person , String command , Location location){
        this.id = id;
        this.command = command;
        this.location = location;
        this.person = person;
    }


    public int getId() {
        return id;
    }

    public String getPerson(){
        return person;
    }

    public Location getLocation() {
        return location;
    }

    public String getCommand() {
        return command;
    }
}
