import java.util.*;

public class Main {
    /*
     - реализовать методы класса CoolNumbers
     - посчитать время поиска введимого номера в консоль в каждой из структуры данных
     - проанализоровать полученные данные
     */

    static String number = "А123ВС66";

    public static void main(String[] args) {
        List<String> coolNumbers1 = CoolNumbers.generateCoolNumbers();
        // перебором в List
        long start1 = System.nanoTime();
        boolean result1 = CoolNumbers.bruteForceSearchInList(coolNumbers1, number);
        long timeCode1 = System.nanoTime() - start1;

        if (result1) {
            System.out.println("Поиск перебором: номер найден, поиск занял " + timeCode1 + "нс");
        } else {
            System.out.println("Поиск перебором: номер не найден, поиск занял " + timeCode1 + "нс");
        }


        // бинарный поиск
        List<String> coolNumbers2 = CoolNumbers.generateCoolNumbers();
        Collections.sort(coolNumbers2);
        long start2 = System.nanoTime();
        boolean result2 = CoolNumbers.binarySearchInList(coolNumbers2, number);
        long timeCode2 = System.nanoTime() - start2;

        if (result2) {
            System.out.println("Бинарный поиск: номер найден, поиск занял " + timeCode2 + "нс");
        } else {
            System.out.println("Бинарный поиск: номер не найден, поиск занял " + timeCode2 + "нс");
        }

        // поиск в HashSet
        List<String> coolNumbers3 = CoolNumbers.generateCoolNumbers();
        HashSet<String> coolNumber3Set = new HashSet<>();
        coolNumber3Set.addAll(coolNumbers3);

        long start3 = System.nanoTime();
        boolean result3 = CoolNumbers.searchInHashSet(coolNumber3Set, number);
        long timeCode3 = System.nanoTime() - start3;

        if (result3) {
            System.out.println("Поиск в HashSet: номер найден, поиск занял " + timeCode3 + "нс");
        } else {
            System.out.println("Поиск в HashSet: номер не найден, поиск занял " + timeCode3 + "нс");
        }
        // поиск в TreeSet
        List<String> coolNumbers4 = CoolNumbers.generateCoolNumbers();
        TreeSet<String> coolNumber4Set = new TreeSet<>();
        coolNumber4Set.addAll(coolNumbers4);

        long start4 = System.nanoTime();
        boolean result4 = CoolNumbers.searchInTreeSet(coolNumber4Set, number);
        long timeCode4 = System.nanoTime() - start4;

        if (result4) {
            System.out.println("Поиск в TreeSet: номер найден, поиск занял " + timeCode4 + "нс");
        } else {
            System.out.println("Поиск в TreeSet: номер не найден, поиск занял " + timeCode4 + "нс");
        }

    }

    }

