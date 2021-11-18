package net.kumc.lab.commandblocklogger;

import org.bukkit.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataList {

    /**
     * データ上に同じ座標のコマンドブロックのデータがあるかどうか,ある場合はそのIDを返す/ない場合は新しいIDを返す
     * @param loc　CommandBlock Location
     * @return ID
     */
    public static int getAvailableID(List<LogData> lists,Location loc) {
        int id ;

        //データ上に同じ座標のコマンドブロックのデータがあるかどうか,ある場合はそのIDを返す
        for(int i = 0; i < lists.size();i++){
            Location locData = lists.get(i).getLocation();
            if(locData.getWorld().getName().equals(loc.getWorld().getName())){
                if(locData.getX()==loc.getX() && locData.getY()==loc.getY() && locData.getZ()==loc.getZ()) {
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
     * @param logData latestLogData
     */
    public static List<LogData> setData(List<LogData> lists,LogData logData){
        int id = logData.getId();

        //最新のデータへの置き換え
        for(int i = 0; i < lists.size(); i++){
            if(lists.get(i).getId() == id){
                lists.set(i , logData);
                return lists;
            }
        }

        //新しいデータの登録
        lists.add(logData);
        return lists;
    }

    /**
     * IDからログデータの探索
     * @param id idNumber
     * @return LogData or null
     */
    public static LogData getTargetData(List<LogData> lists,int id){

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
     * @param str String
     * @return LogID or null
     */
    public static List<Integer> getTargetString(List<LogData> lists,String str){
        List<Integer> returnList = new ArrayList<>();

        //文字列の探索
        for(int i = 0; i < lists.size();i++){
            if(lists.get(i).getCommand().matches(".*"+str+".*")){
                returnList.add(lists.get(i).getId());
            }
        }
        return returnList;

    }

    /**
     * configに設定された時間以上変更が加えられていないデータは削除する
     * @return CheckedData
     * @throws ParseException
     */
    public static List<LogData> setCheckedData(List<LogData> lists) throws ParseException {

        //最新のデータへの置き換え
        for(int i = lists.size()-1; i >= 0; i--){
            //日時の差分計算
            Date dateObject = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd|HH:mm:ss");

            Date pastDate = format.parse(lists.get(i).getTime());
            Date nowDate = format.parse(format.format(dateObject));
            long diffSec = (nowDate.getTime()-pastDate.getTime()) / 1000;
            if(diffSec > CommandBlockLogger.INSTANCE.getConfig().getInt("checkIntervalSec")){
                lists.remove(i);
            }
        }

        return lists;
    }

}

