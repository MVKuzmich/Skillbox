import java.util.Arrays;

public class ReverseArray {
    //Напишите код, который меняет порядок расположения элементов внутри массива на обратный.
    public static String[] reverse (String[] strings){
        for (int i = strings.length / 2; i >= 0; i--) {
            String temp = strings [i];
            strings[i] = strings[strings.length - 1 - i];
            strings[strings.length - 1 - i] = temp;
        }
        return strings;
    }
}
