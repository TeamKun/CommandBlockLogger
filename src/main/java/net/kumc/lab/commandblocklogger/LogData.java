package net.kumc.lab.commandblocklogger;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import org.bukkit.Location;

public class LogData {

    @Getter
    @CsvBindByPosition(position = 0)
    private int id;
    @Getter
    @CsvBindByPosition(position = 1)
    private String command;
    @Getter
    @CsvBindByPosition(position = 2)
    private Location location;
    @Getter
    @CsvBindByPosition(position = 3)
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
}
