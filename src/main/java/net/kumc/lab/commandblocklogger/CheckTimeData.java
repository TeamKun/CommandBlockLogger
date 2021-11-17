package net.kumc.lab.commandblocklogger;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.ParseException;

public class CheckTimeData {

    public static void checkTimeData(){
        int time = CommandBlockLogger.INSTANCE.getConfig().getInt("checkIntervalSec") * 20;
        new BukkitRunnable() {
            public void run() {
                try {
                    DataList.setCheckedData(CommandBlockLogger.allLog);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(CommandBlockLogger.INSTANCE,time,time);
    }

}
