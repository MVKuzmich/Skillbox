import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            String regex1 = "[^0-9]";

            String numberRange = input.replaceAll(regex1, "").trim();
            if (numberRange.length() == 11 && (numberRange.charAt(0) == '7' || numberRange.charAt(0) == '8')) {
                if (numberRange.charAt(0) == '7') {
                    System.out.println(numberRange);
                }
                if (numberRange.charAt(0) == '8') {
                    System.out.println(numberRange.replace('8', '7'));
                }
            } else if (numberRange.length() == 10) {
                System.out.println("7" + numberRange);
            } else {
                System.out.println("Неверный формат номера");
            }
        }
    }

}
