import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by locdt on 11/16/2017.
 */
public class Utils {
    public static String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    public static String removeSpaceHTML(String html) {
        return html.replace("&nbsp;"," ");
    }

    public static void appendDataToFile(String filePath, String data) throws Exception {
        Files.write(Paths.get(filePath), data.getBytes(), StandardOpenOption.APPEND);
    }
}
