import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            int spaceCount = 0;
            for (int j = 0; j < input.length(); j++) {
                char space = input.charAt(j);
                if (space == ' ') {
                    spaceCount++;
                }
            }
            char cyrillicLetter = 0;
            for (int i = 0; i < input.length(); i++) {
                cyrillicLetter = input.charAt(i);
            }
            if (spaceCount == 2 && Character.UnicodeBlock.of(cyrillicLetter).equals((Character.UnicodeBlock.CYRILLIC)) ||
                    cyrillicLetter == '-') {

                System.out.print("Фамилия: " + input.substring(0, input.indexOf(' ')) + System.lineSeparator() +
                        "Имя: " + input.substring(input.indexOf(' ', 0), input.lastIndexOf(' ')).trim()
                        + System.lineSeparator() +
                        "Отчество: " + input.substring(input.lastIndexOf(' ')).trim());

            } else {
                System.out.println("Введенная строка не является ФИО");
            }


        }
    }
}



