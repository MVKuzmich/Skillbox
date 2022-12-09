import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        long bytesToKilo = 1_024;
        long bytesToMega = 1_048_576;
        long bytesToGiga = 1_073_741_824;

        while (true) {
            System.out.println("Введите путь до папки: ");

            Scanner scanner = new Scanner(System.in);

            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            try {
      //        long pathFolderSize = FileUtils.calculateFolderSize(input);
                long pathFolderSize = FileUtils.calculateFolderSize(input);
                String forPrint = "Размер папки " + input + " составляет ";

                if (pathFolderSize <= bytesToKilo) {
                    System.out.println(forPrint + pathFolderSize + " байт");
                } else if (pathFolderSize > bytesToKilo && pathFolderSize <= bytesToMega) {
                    System.out.println(forPrint + (Math.round((pathFolderSize / bytesToKilo) / 100) * 100L) + " кБ");
                } else if (pathFolderSize > bytesToMega && pathFolderSize <= bytesToGiga) {
                    System.out.println(forPrint + (Math.round((pathFolderSize / bytesToMega) / 100) * 100L) + " МБ");
                } else {
                    System.out.println(forPrint + (Math.round((pathFolderSize / bytesToGiga) / 100) * 100L) + " ГБ");
                }
            } catch (IOException ex) {
                ex.getMessage();
            }


        }
    }
}



