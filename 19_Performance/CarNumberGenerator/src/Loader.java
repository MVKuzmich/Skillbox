import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Loader {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        List<Thread> threadList = new ArrayList<>();

        int end = 20;
        int begin = 1;
        while (end <= 100) {
            Thread thread = new Thread(new Task(begin, end));
            thread.start();
            threadList.add(thread);
            begin = end;
            end = end + 20;

        }
        for (Thread thread : threadList) {
            thread.join();
        }

        System.out.println("Время работы основного потока " + (System.currentTimeMillis() - start) + " ms");
    }


}
