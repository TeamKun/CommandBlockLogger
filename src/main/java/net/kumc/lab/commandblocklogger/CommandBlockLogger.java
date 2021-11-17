package net.kumc.lab.commandblocklogger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class CommandBlockLogger extends JavaPlugin {

    public static CommandBlockLogger INSTANCE;
    //ログ保存用のディレクトリの生成
    public static File logsDirectory;
    //ログのデータリスト
    public static List<LogData> allLog = new ArrayList<>();


    //実行時処理
    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        //Event登録
        Bukkit.getPluginManager().registerEvents(new EventHandler(),this);

        //コマンドの登録
        Objects.requireNonNull(this.getCommand("cblogger")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("cblogger")).setTabCompleter(new Commands());

        //ログ保存用のディレクトリの生成
        logsDirectory = new File(getDataFolder(),"logs");
        logsDirectory.mkdirs();

        //log.csvの生成
        try {
            File file = new File(logsDirectory+"\\log.csv");
            file.createNewFile();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex){
            ex.printStackTrace();
        }

        //csvの読み込み
        allLog = FileUtil.readLogFile();

        //サーバログ
        getServer().getLogger().info(ChatColor.AQUA + "CommandBlockLogger by Yanaaaaa");

        //データの時間チェックのスタート
        CheckTimeData.checkTimeData();
    }

    @Override
    public void onDisable() {
        //csvの書き込み
        FileUtil.writeLogFile(allLog);
    }
}
