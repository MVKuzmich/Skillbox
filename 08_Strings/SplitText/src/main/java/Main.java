public class Main {

    public static void main(String[] args) {
        String text = "Oracle8 today 325 announced? the !availability of Java 17, the latest version of the world’s number";

        System.out.print(splitTextIntoWords(text));
    }

    public static String splitTextIntoWords(String text) {
        String textColumn = "";
        String[] words = text.split("[\\s,-.!?:;(0-9)]+");
        for (int i = 0; i < words.length; i++) {
            textColumn = textColumn + words[i] + System.lineSeparator();
        }
        return textColumn.trim();
    }
}