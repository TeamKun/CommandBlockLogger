package net.kumc.lab.commandblocklogger;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileUtil {

    /**
     * csvFileへのログの書き込み
     * @param dataList Log Data
     */
    public static void writeLogFile(List<LogData> dataList){
        try {
            //CSVデータファイル
            File csv = new File(CommandBlockLogger.logsDirectory+"\\log.csv");
            //ファイルの読み込み
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));

            for(int i = 0; i < dataList.size();i++) {
                LogData logData = dataList.get(i);
                //文字列の抽出
                String id = String.valueOf(logData.getId());
                String time = logData.getTime();
                String command = logData.getCommand();
                Location loc = logData.getLocation();
                String location = loc.getWorld().getName()+"/"+String.valueOf(loc.getBlockX())+"/"+String.valueOf(loc.getBlockY())+"/"+String.valueOf(loc.getBlockZ());

                bw.write(id +","+ time +","+ command +","+ location);
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * csvFileからのログの読み込み
     * @return csv Data
     */
    public static List<LogData> readLogFile(){
        List<LogData> dataList = new ArrayList<>();
        try {
            //CSVデータファイル
            File csv = new File(CommandBlockLogger.logsDirectory+"\\log.csv");
            //ファイルの読み込み
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";

            while ((line = br.readLine()) != null) {
                // 1行をデータの要素に分割
                StringTokenizer st = new StringTokenizer(line, ",");

                int count = 1;

                int id = 0;
                String time = null;
                String command = null;
                Location location = new Location(Bukkit.getWorld("world"),0,0,0);

                //各要素を文字列から変換
                while (st.hasMoreTokens()) {
                    //IDの抽出
                    if(count==1){
                        id = Integer.parseInt(st.nextToken());
                    }
                    //時間の抽出
                    if(count==2){
                        time = st.nextToken();
                    }
                    //コマンドの抽出
                    if(count==3){
                        command = st.nextToken();
                    }
                    //Locationの抽出
                    if(count==4){
                        double X ,Y ,Z;
                        int count2 = 1;
                        // 1行をデータの要素に分割
                        StringTokenizer locStr = new StringTokenizer(st.nextToken(), "/");

                        //ディメンジョン、各座標の抽出およびlocationの設定
                        while (locStr.hasMoreTokens()) {
                            if(count2==1){
                                location.setWorld(Bukkit.getWorld(locStr.nextToken()));
                            }
                            if(count2==2){
                                X = Integer.parseInt(locStr.nextToken());
                                location.setX(X);
                            }
                            if(count2==3){
                                Y = Integer.parseInt(locStr.nextToken());
                                location.setY(Y);
                            }
                            if(count2==4){
                                Z = Integer.parseInt(locStr.nextToken());
                                location.setZ(Z);
                            }
                            count2++;
                        }
                    }
                    //Logデータ
                    LogData logData = new LogData(id,time,command,location);
                    dataList = DataList.setData(dataList,logData);

                    count++;
                }
            }
            br.close();

        }catch (FileNotFoundException ex) {

        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return dataList;
    }
}