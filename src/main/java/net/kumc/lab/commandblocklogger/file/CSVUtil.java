package net.kumc.lab.commandblocklogger.file;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import net.kumc.lab.commandblocklogger.CommandBlockLogger;
import net.kumc.lab.commandblocklogger.data.LogData;

import java.io.*;
import java.util.List;

public class CSVUtil {
    /**
     * csvFileへのログの書き込み
     *
     * @param dataList Log Data
     */
    public static void writeLogFile(List<LogData> dataList) {
        try {
            String file = CommandBlockLogger.logsDirectory + File.separator + "log.csv";
            Writer writer = new FileWriter(file);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(dataList);
            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * csvFileからのログの読み込み
     *
     * @return csv Data
     */
    public static List<LogData> readLogFile() {
        List<LogData> dataList = null;
        try {
            String file = CommandBlockLogger.logsDirectory + File.separator + "log.csv";
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            CsvToBeanBuilder<LogData> builder = new CsvToBeanBuilder<>(br);
            builder.withType(LogData.class);

            dataList = builder.build().parse();

            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return dataList;
    }
}
