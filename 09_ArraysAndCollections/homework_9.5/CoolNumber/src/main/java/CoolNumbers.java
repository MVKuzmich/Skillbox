import java.util.*;

public class CoolNumbers {
       private static List<String> coolNumbers = new ArrayList<>();

        public static List<String> generateCoolNumbers() {
        String[] letters = new String[] {"А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х"};
        int count = 0;
        String template = "%s%d%d%d%s%s%02d";
            for (int i = 0; i < letters.length; i++) {
                for (int m = 0; m < 10; m++) {
                    for (int l = 0; l < 10; l++) {
                        for (int b = 0; b < 10; b++) {
                            for (int j = 0; j < letters.length; j++) {
                                for (int k = 0; k < letters.length; k++) {
                                    for (int n = 1; n < 200; n++) {
                                        if ((letters[i].equals(letters[j]) && letters[i].equals(letters[k])) ||
                                                (m == l && m == b)) {
                                            {
                                                String coolNumber = String.format(template, letters[i], m, l, b, letters[j], letters[k], n);
                                                coolNumbers.add(coolNumber);
                                                count++;
                                                if (count > 2000002) {
                                                    break;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        return coolNumbers;
    }

    public static boolean bruteForceSearchInList(List<String> list, String number) {
            return list.contains(number);
            }



    public static boolean binarySearchInList(List<String> sortedList, String number) {
        int index = Collections.binarySearch(sortedList, number);
        if (index < 0) {
            return false;
        }
        return true;
    }


    public static boolean searchInHashSet(HashSet<String> hashSet, String number) {
            return hashSet.contains(number);
    }

    public static boolean searchInTreeSet(TreeSet<String> treeSet, String number) {
        return treeSet.contains(number);
    }

}
