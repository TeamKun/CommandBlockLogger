package net.kumc.lab.commandblocklogger.event;

import net.kumc.lab.commandblocklogger.CommandBlockLogger;
import net.kumc.lab.commandblocklogger.data.DataUtil;
import net.kumc.lab.commandblocklogger.data.LogData;
import net.kumc.lab.commandblocklogger.data.PlacerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class EventHandler implements Listener {
    public static List<Block> blockList = new ArrayList<>();
    //コマンドブロック設置時
    @org.bukkit.event.EventHandler
    public void onPlaceCommandBlock(BlockPlaceEvent event){
        //コマンドブロックでなければreturn
        Material material = event.getBlock().getType();
        if (!CommandBlockLogger.materials.contains(material)) return;
        //設置者のデータに追加
        DataUtil.setPlacerData(CommandBlockLogger.placerData, new PlacerData(event.getPlayer().getName(), event.getBlock().getLocation()));
    }

    //コマンドブロック破壊時
    @org.bukkit.event.EventHandler
    public void onBreakCommandBlock(BlockBreakEvent event){
        //コマンドブロックでなければreturn
        Material material = event.getBlock().getType();
        if(!CommandBlockLogger.materials.contains(material)) return;

        //ブロックのデータチェック
        new BukkitRunnable() {
            public void run() {
                DataUtil.setCheckedData(CommandBlockLogger.allLog, CommandBlockLogger.placerData);
            }
        }.runTaskLater(CommandBlockLogger.INSTANCE, 20);
    }

    //コマンドブロック実行時
    @org.bukkit.event.EventHandler
    public void onWorksCommandBlock(ServerCommandEvent event){
        if (event.getSender() instanceof BlockCommandSender) {
            //Locationの抽出
            BlockCommandSender sender = (BlockCommandSender) event.getSender();
            Location location = sender.getBlock().getLocation();

            //Blockの抽出
            Block block = location.getBlock();
            if(blockList.contains(block)) return;

            //リピートコマンドブロックなら実行
            if (block.getType() == Material.COMMAND_REPEATING) {
                //コマンドの抽出
                String command = event.getCommand();
                if (command.equals(""))return;

                //コマンド変更がなければreturn
                if (DataUtil.onCheckBlock(CommandBlockLogger.allLog,location,command)){
                    blockList.add(block);
                    return;
                }

                //設置者取得
                String placer = DataUtil.getPlacer(CommandBlockLogger.placerData, location);
                if (placer.equals("")) return;

                //id取得
                int id = DataUtil.getAvailableID(CommandBlockLogger.allLog, location);

                //logへの追加
                LogData logData = new LogData(id, placer, command, location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
                DataUtil.setData(CommandBlockLogger.allLog, logData);
            }
        }
    }
}
