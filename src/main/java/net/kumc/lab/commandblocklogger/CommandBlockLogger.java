package net.kumc.lab.commandblocklogger;

import net.kumc.lab.commandblocklogger.command.CommandListener;
import net.kumc.lab.commandblocklogger.command.TabCompleter;
import net.kumc.lab.commandblocklogger.data.CheckBlockData;
import net.kumc.lab.commandblocklogger.data.LogData;
import net.kumc.lab.commandblocklogger.data.PlacerData;
import net.kumc.lab.commandblocklogger.event.EventHandler;
import net.kumc.lab.commandblocklogger.file.CSVUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class CommandBlockLogger extends JavaPlugin {

    public static CommandBlockLogger INSTANCE;
    //ログ保存用のディレクトリの生成
    public static File logsDirectory;
    //ログのデータリスト
    public static List<LogData> allLog = new ArrayList<>();
    //ブロックの設置者リスト
    public static List<PlacerData> placerData = new ArrayList<>();

    public static List<Material> materials = new ArrayList<>();

    //実行時処理
    @Override
    public void onEnable() {
        INSTANCE = this;

        //コマンドブロック
        materials.add(Material.COMMAND);
        materials.add(Material.COMMAND_CHAIN);
        materials.add(Material.COMMAND_REPEATING);

        //ログ保存用のディレクトリの生成
        logsDirectory = new File(getDataFolder(), "logs");
        logsDirectory.mkdirs();

        //log.csvの生成
        try {
            File csv = new File(CommandBlockLogger.logsDirectory + File.separator + "log.csv");
            csv.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //csvの読み込み
        allLog = CSVUtil.readLogFile();

        //Event登録
        Bukkit.getPluginManager().registerEvents(new EventHandler(), INSTANCE);

        //コマンドの登録
        Objects.requireNonNull(this.getCommand("cblogger")).setExecutor(new CommandListener());
        Objects.requireNonNull(this.getCommand("cblogger")).setTabCompleter(new TabCompleter());

        //サーバログ
        getServer().getLogger().info(ChatColor.AQUA + "CommandBlockLogger by KUNLab");

        //データのチェックのスタート
        CheckBlockData.checkData();
    }

    @Override
    public void onDisable() {
        //csvの書き込み
        CSVUtil.writeLogFile(allLog);
    }
}
