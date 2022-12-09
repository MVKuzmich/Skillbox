import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

            System.out.println("Введите путь директория - источника: ");
            Scanner scanner = new Scanner(System.in);

            String source = scanner.nextLine();
            System.out.println("Введите путь директория - получателя: ");

            String destination = scanner.nextLine();

        try {
            FileUtils.copyFolder(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
