package net.kumc.lab.commandblocklogger;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CheckBlockData {

    public static void checkData() {
        //reload時の例外削除
        List<Integer> illegalNum = new ArrayList<>();
        for (int i = 0; i < CommandBlockLogger.allLog.size(); i++) {
            if (i != CommandBlockLogger.allLog.size() - 1) {
                if (CommandBlockLogger.allLog.get(i).getId() == CommandBlockLogger.allLog.get(i + 1).getId()) {
                    illegalNum.add(i + 1);
                }
            }
        }

        for (int i = illegalNum.size() - 1; i >= 0; i--) {
            int num = illegalNum.get(i);
            CommandBlockLogger.allLog.remove(num);
        }

        new BukkitRunnable() {
            public void run() {
                DataUtil.setCheckedData(CommandBlockLogger.allLog, CommandBlockLogger.placerData);
            }
        }.runTaskTimer(CommandBlockLogger.INSTANCE, 0, 200);
    }
}
