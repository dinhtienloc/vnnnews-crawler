import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by locdt on 11/16/2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Delete previous data
            PrintWriter pw = new PrintWriter(Constants.JSON_FILE);
            pw.close();

            Utils.appendDataToFile(Constants.JSON_FILE, "[");
            ExecutorService executor = Executors.newFixedThreadPool(Constants.THREAD_NUM);
            Runnable crawler1 = new Crawler("16/11/2017");
            executor.execute(crawler1);

            Runnable crawler2 = new Crawler("15/11/2017");
            executor.execute(crawler2);

            Runnable crawler3 = new Crawler("14/11/2017");
            executor.execute(crawler3);

            Runnable crawler4 = new Crawler("13/11/2017");
            executor.execute(crawler4);

            Runnable crawler5 = new Crawler("12/11/2017");
            executor.execute(crawler5);

            executor.shutdown();
            while (!executor.isTerminated()) {}
            System.out.println("Finished all threads");
            Utils.appendDataToFile(Constants.JSON_FILE, "]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
