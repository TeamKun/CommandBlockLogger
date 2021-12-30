package net.kumc.lab.commandblocklogger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onWorksCommandBlock(ServerCommandEvent event){
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
                if(command.equals(""))
                    return;

                //設置者取得
                String placer = DataUtil.getPlacer(CommandBlockLogger.placerData,location);
                if(placer.equals(""))
                    return;

                //id取得
                int id = DataUtil.getAvailableID(CommandBlockLogger.allLog,location);

                //logへの追加
                LogData logData = new LogData(id , placer , command , location);
                DataUtil.setData(CommandBlockLogger.allLog, logData);

            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onInstallationCommandBlock(BlockPlaceEvent event){
        Material material = event.getBlock().getType();
        //コマンドブロックが設置された場合のみ実行
        if(material==Material.COMMAND||material==Material.COMMAND_REPEATING||material==Material.COMMAND_CHAIN||material==Material.COMMAND_MINECART){
            //設置者のデータに追加
            DataUtil.setPlacerData(CommandBlockLogger.placerData, new PlacerData(event.getPlayer().getName(), event.getBlock().getLocation()));
        }
    }

    @org.bukkit.event.EventHandler
    public void onBlockBlake(BlockBreakEvent event) {
        Material material = event.getBlock().getType();
        //コマンドブロックが破壊された場合のみ実行
        if(material==Material.COMMAND||material==Material.COMMAND_REPEATING||material==Material.COMMAND_CHAIN||material==Material.COMMAND_MINECART){
            //ブロックのデータチェック
            new BukkitRunnable() {
                public void run() {
                    DataUtil.setCheckedData(CommandBlockLogger.allLog,CommandBlockLogger.placerData);
                }
            }.runTaskLater(CommandBlockLogger.INSTANCE,20);
        }
    }

}

