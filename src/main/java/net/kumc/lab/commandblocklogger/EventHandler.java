package net.kumc.lab.commandblocklogger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventHandler implements Listener {


    @org.bukkit.event.EventHandler
    public void onCommandBlockCheck(ServerCommandEvent event){
        if(event.getSender() instanceof BlockCommandSender) {
            //Locationの抽出
            BlockCommandSender sender = (BlockCommandSender) event.getSender();
            Location location = sender.getBlock().getLocation();

            //Blockの抽出
            Block block = location.getBlock();

            //リピートコマンドブロックなら実行
            if(block.getType()== Material.COMMAND_REPEATING) {
                //コマンドの抽出
                String command = event.getCommand();

                //日時取得
                Date dateObject = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd|HH:mm:ss");
                String timeData = format.format(dateObject);

                //id取得
                int id = DataList.getAvailableID(CommandBlockLogger.allLog,location);

                //logへの追加
                LogData logData = new LogData(id , timeData , command , location);
                DataList.setData(CommandBlockLogger.allLog, logData);

            }

        }
    }


}

