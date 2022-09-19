package net.kumc.lab.commandblocklogger;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    /**
     * データ上に同じ座標のコマンドブロックのデータがあるかどうか,ある場合はそのIDを返す/ない場合は新しいIDを返す
     *
     * @param loc 　CommandBlock Location
     * @return ID
     */
    public static int getAvailableID(List<LogData> lists, Location loc) {
        int id;

        //データ上に同じ座標のコマンドブロックのデータがあるかどうか,ある場合はそのIDを返す
        for (int i = 0; i < lists.size(); i++) {
            Location locData = lists.get(i).getLocation();
            if (locData.getWorld().getName().equals(loc.getWorld().getName())) {
                if (locData.getX() == loc.getX() && locData.getY() == loc.getY() && locData.getZ() == loc.getZ()) {
                    id = lists.get(i).getId();
                    return id;
                }
            }
        }

        if (lists.size() == 0) {
            id = 1;
        } else {
            id = lists.get(lists.size() - 1).getId() + 1;
        }

        return id;
    }

    /**
     * データ上に同じ座標のコマンドブロックのデータがあるかどうか,ある場合は同IDのデータの置換/ない場合は新しく追加
     *
     * @param logData latestLogData
     */
    public static List<LogData> setData(List<LogData> lists, LogData logData) {
        int id = logData.getId();

        //最新のデータへの置き換え
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getId() == id) {
                if (!(!lists.get(i).getPerson().equals("-NoData-") && logData.getPerson().equals("-NoData-"))) {
                    lists.set(i, logData);
                }
                return lists;
            }
        }

        //新しいデータの登録
        lists.add(logData);

        return lists;
    }

    /**
     * IDからログデータの探索
     *
     * @param id idNumber
     * @return LogData or null
     */
    public static LogData getTargetData(List<LogData> lists, int id) {

        //IDの探索
        for (LogData list : lists) {
            if (list.getId() == id) {
                return list;
            }
        }
        return null;
    }

    /**
     * 文字列からログデータの探索
     *
     * @param str String
     * @return LogID or null
     */
    public static List<Integer> getTargetString(List<LogData> lists, String str) {
        List<Integer> returnList = new ArrayList<>();

        //文字列の探索
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getCommand().matches(".*" + str + ".*")) {
                returnList.add(lists.get(i).getId());
            }
        }
        return returnList;

    }

    /**
     * リストのlocationデータにリピートコマンドブロックあるかの確認、無ければデータの削除
     *
     * @return CheckedData
     */
    public static List<LogData> setCheckedData(List<LogData> lists, List<PlacerData> placerData) {
        //最新のデータへの置き換え
        for (int i = lists.size() - 1; i >= 0; i--) {
            if (lists.get(i).getLocation().getBlock().getType() != Material.COMMAND_REPEATING) {
                for (int j = placerData.size() - 1; j >= 0; j--) {
                    if (placerData.get(j).getLocation() == lists.get(i).getLocation()) {
                        placerData.remove(j);
                    }
                }
                lists.remove(i);
            }
        }

        return lists;
    }

    /**
     * リスト内のロケーションと一致した場合、"設置者"を返す
     *
     * @param list ブロックの設置者のデータ
     * @param loc  場所
     * @return ブロックの設置者(データがない場合はNoDataを返す)
     */
    public static String getPlacer(List<PlacerData> list, Location loc) {
        String str = "-NoData-";
        for (int i = 0; i < list.size(); i++) {
            Location locData = list.get(i).getLocation();
            if (locData.getWorld().getName().equals(loc.getWorld().getName())) {
                if (locData.getX() == loc.getX() && locData.getY() == loc.getY() && locData.getZ() == loc.getZ()) {
                    str = list.get(i).getPlacer();
                }
            }
        }

        return str;
    }

    /**
     * @param list       設置者のリスト
     * @param placerData 　設置者のデータ
     * @return 処理後のリスト
     */
    public static List<PlacerData> setPlacerData(List<PlacerData> list, PlacerData placerData) {
        Location loc = placerData.getLocation();
        for (int i = 0; i < list.size(); i++) {
            Location locData = list.get(i).getLocation();
            if (locData == loc) {
                list.set(i, placerData);
                return list;
            }
        }
        list.add(placerData);
        return list;
    }

}

