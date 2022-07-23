import java.util.Arrays;

public class Main {

    public static char twoDimenArray [][] = TwoDimensionalArray.getTwoDimensionalArray(7);

    public static void main(String[] args) {
        for (int i = 0; i < twoDimenArray.length; i++) {
            for (int j = 0; j < twoDimenArray.length; j++) {
                System.out.print(twoDimenArray[i][j]);
            }
            System.out.println();

            }
        }
        //Распечатайте сгенерированный в классе TwoDimensionalArray.java двумерный массив
    }

