import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        String line = "Каждый охотник желает знать, где сидит фазан";
        String[] strings = line.split(",?\\s+");
        System.out.println(Arrays.toString(ReverseArray.reverse(strings)));
    }
}
