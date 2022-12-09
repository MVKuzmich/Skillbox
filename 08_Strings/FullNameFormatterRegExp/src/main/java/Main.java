import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            String regex = "[А-яЁё-]+[\\s][А-яЁё-]+[\\s][А-яЁё-]+";
            if (input.matches(regex)) {
                String[] personName = input.split("\\s");
                System.out.print("Фамилия: " + personName[0] + System.lineSeparator() +
                        "Имя: " + personName[1] + System.lineSeparator() +
                        "Отчество: " + personName[2]);
            } else {
                System.out.println("Введенная строка не является ФИО");
            }
        }
    }
}