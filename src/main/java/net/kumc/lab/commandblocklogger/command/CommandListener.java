package net.kumc.lab.commandblocklogger.command;

import net.kumc.lab.commandblocklogger.CommandBlockLogger;
import net.kumc.lab.commandblocklogger.data.DataUtil;
import net.kumc.lab.commandblocklogger.data.LogData;
import net.kumc.lab.commandblocklogger.file.CSVUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class CommandListener implements CommandExecutor {
    /**
     * Command
     */
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (cmd.getName().equals("cblogger")) {
            if (args.length == 1) {
                if (args[0].equals("save")) {
                    CSVUtil.writeLogFile(CommandBlockLogger.allLog);
                    sender.sendMessage(ChatColor.GREEN + "[CBLogger]log.csvにデータの保存処理を行いました.(保存されていない場合はコンソールを確認してください)");
                } else if (args[0].equals("list")) {
                    List<LogData> list = CommandBlockLogger.allLog;
                    sender.sendMessage(ChatColor.GREEN + "[CBLogger]検索結果:" + list.size() + "件");
                    if (list.size() != 0) {
                        sender.sendMessage(ChatColor.GREEN + "[CBLogger]一致したコマンドブロックのデータを表示します.");
                        sender.sendMessage(ChatColor.GOLD + "-------------LogData-------------");
                        //取得したリストの表示
                        for (int i = 0; i < list.size(); i++) {
                            LogData logData = list.get(i);
                            String loc = logData.getLocation().getWorld().getName() + "/" + logData.getLocation().getX() + "/" + logData.getLocation().getY() + "/" + logData.getLocation().getZ();
                            sender.sendMessage("ID: " + ChatColor.AQUA + logData.getId() + ChatColor.WHITE + ", Player: " + ChatColor.AQUA + logData.getPerson() + ChatColor.WHITE + ", Command: " + ChatColor.AQUA + logData.getCommand() + ChatColor.WHITE + ", Location: " + ChatColor.AQUA + loc);
                        }
                        sender.sendMessage(ChatColor.GOLD + "---------------------------------");
                    }

                } else if (args[0].equals("help")) {
                    sender.sendMessage(ChatColor.GREEN + "-----------------コマンド一覧-----------------");
                    sender.sendMessage(ChatColor.GOLD + "・直近に動作した全リピートコマンドブロックの表示");
                    sender.sendMessage("/cblogger list");
                    sender.sendMessage(ChatColor.GOLD + "・現在のデータをlog.csvに書き出し");
                    sender.sendMessage("/cblogger save");
                    sender.sendMessage(ChatColor.GOLD + "・コマンド一覧の表示");
                    sender.sendMessage("/cblogger help");
                    sender.sendMessage(ChatColor.GOLD + "・指定したコマンドブロックにTP(IDはlist・searchコマンドから確認してください)");
                    sender.sendMessage("/cblogger tp <ID>");
                    sender.sendMessage(ChatColor.GOLD + "・指定したコマンドブロックを削除(IDはlist・searchコマンドから確認してください)");
                    sender.sendMessage("/cblogger delete <ID>");
                    sender.sendMessage(ChatColor.GOLD + "・指定したワードから、一致したリピートコマンドブロックの表示(like検索可能)");
                    sender.sendMessage("/cblogger search　<検索単語>");
                    sender.sendMessage(ChatColor.GREEN + "--------------------------------------------");
                } else {
                    sender.sendMessage(ChatColor.RED + "[CBLogger]コマンドが異なります、形式を確認してください. コマンド一覧:/cblogger help");
                }
            } else if (args.length == 2) {
                if (args[0].equals("tp") && args[1].matches("[+-]?\\d*(\\.\\d+)?")) {
                    int id = Integer.parseInt(args[1]);
                    if (DataUtil.getTargetData(CommandBlockLogger.allLog, id) == null) {
                        sender.sendMessage(ChatColor.RED + "[CBLogger]そのIDのデータ,コマンドブロックは存在しません.");
                    } else {
                        Location loc = DataUtil.getTargetData(CommandBlockLogger.allLog, Integer.parseInt(args[1])).getLocation();
                        //IDのデータ存在するかどうか
                        if (loc == null) {
                            sender.sendMessage(ChatColor.RED + "[CBLogger]そのIDのデータ,コマンドブロックは存在しません.");
                        } else {
                            Player p = (Player) sender;
                            p.teleport(loc);
                            sender.sendMessage(ChatColor.GREEN + "[CBLogger]ID=" + args[1] + "のコマンドブロックにTPしました.");
                        }
                    }
                } else if (args[0].equals("delete") && args[1].matches("[+-]?\\d*(\\.\\d+)?")) {
                    int id = Integer.parseInt(args[1]);
                    if (DataUtil.getTargetData(CommandBlockLogger.allLog, id) == null) {
                        sender.sendMessage(ChatColor.RED + "[CBLogger]そのIDのデータ,コマンドブロックは存在しません.");
                    } else {
                        Location loc = DataUtil.getTargetData(CommandBlockLogger.allLog, Integer.parseInt(args[1])).getLocation();
                        //IDのデータ存在するかどうか
                        if (loc == null) {
                            sender.sendMessage(ChatColor.RED + "[CBLogger]そのIDのデータ,コマンドブロックは存在しません.");
                        } else {
                            loc.getBlock().setType(Material.AIR);
                            DataUtil.setCheckedData(CommandBlockLogger.allLog, CommandBlockLogger.placerData);
                            sender.sendMessage(ChatColor.GREEN + "[CBLogger]ID=" + args[1] + "のコマンドブロックを削除しました.");
                        }
                    }
                } else if (args[0].equals("search")) {
                    String str = "";

                    //候補IDのリスト
                    List<Integer> integerList = new ArrayList<>();

                    //単語の探索
                    integerList = DataUtil.getTargetString(CommandBlockLogger.allLog, args[1]);

                    //ソート、重複削除
                    integerList = new ArrayList<>(new HashSet<>(integerList));
                    integerList.sort(new Comparator<Integer>() {
                        @Override
                        public int compare(Integer s1, Integer s2) {
                            int i1 = s1;
                            int i2 = s2;
                            return i1 - i2;
                        }
                    });

                    sender.sendMessage(ChatColor.GREEN + "[CBLogger]検索結果:" + integerList.size() + "件");
                    if (integerList.size() != 0) {
                        sender.sendMessage(ChatColor.GREEN + "[CBLogger]一致したコマンドブロックのデータを表示します.");
                        sender.sendMessage(ChatColor.GOLD + "-------------LogData-------------");
                        //取得したリストの表示
                        for (Integer integer : integerList) {
                            LogData logData = DataUtil.getTargetData(CommandBlockLogger.allLog, integer);
                            String loc = logData.getLocation().getWorld().getName() + "/" + String.valueOf(logData.getLocation().getX()) + "/" + String.valueOf(logData.getLocation().getY()) + "/" + String.valueOf(logData.getLocation().getZ());
                            sender.sendMessage("ID: " + ChatColor.AQUA + logData.getId() + ChatColor.WHITE + ", Player: " + ChatColor.AQUA + logData.getPerson() + ChatColor.WHITE + ", Command: " + ChatColor.AQUA + logData.getCommand() + ChatColor.WHITE + ", Location: " + ChatColor.AQUA + loc);
                        }
                        sender.sendMessage(ChatColor.GOLD + "---------------------------------");
                    }

                } else {
                    sender.sendMessage(ChatColor.RED + "[CBLogger]コマンドが異なります、形式を確認してください. コマンド一覧:/cblogger help");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[CBLogger]コマンドが異なります、形式を確認してください. コマンド一覧:/cblogger help");
            }
        }
        return false;
    }
}
