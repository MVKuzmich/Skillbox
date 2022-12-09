import java.util.Scanner;

public class Main {
    public static final int MAX_BOXES_FOR_CONTAINERS = 27;
    public static final int MAX_BOXES_FOR_TRUCKS = 324;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int boxes = scanner.nextInt();

        String tab = "\t";

        if (boxes == 0) {
            System.out.print("Необходимо:" + System.lineSeparator() + "грузовиков - 0 шт." + System.lineSeparator()
                    + "контейнеров - 0 шт.");
        }
        if (boxes > 0) {
            System.out.print("Грузовик: 1" + System.lineSeparator()
                    + tab + "Контейнер: 1" + System.lineSeparator());
            for (int i = 1; i <= boxes; i++) {
                System.out.print(tab + tab + "Ящик: " + i + System.lineSeparator());
                    if (i % MAX_BOXES_FOR_TRUCKS == 0 && boxes % MAX_BOXES_FOR_TRUCKS != 0) {
                        System.out.print("Грузовик: " + ((i / MAX_BOXES_FOR_TRUCKS) + 1) + System.lineSeparator());
                    }
                    if (i % MAX_BOXES_FOR_CONTAINERS == 0 && boxes % MAX_BOXES_FOR_CONTAINERS != 0) {
                        System.out.print(tab + "Контейнер: " + ((i / MAX_BOXES_FOR_CONTAINERS) + 1) + System.lineSeparator());
                    }
                }
                System.out.println("Необходимо:" + System.lineSeparator() + "грузовиков - " +
                        (int) Math.ceil((double) (boxes) / (double) (MAX_BOXES_FOR_TRUCKS))
                        + " шт." + System.lineSeparator() + "контейнеров - " +
                        (int) Math.ceil((double) boxes / (double) MAX_BOXES_FOR_CONTAINERS) + " шт.");
            }
        }
    }


