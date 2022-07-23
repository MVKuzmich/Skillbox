public class Arithmetic {
    int a;
    int b;

    public Arithmetic(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public static int calculateSum(int a, int b) {
        return a + b;
    }

    public static int calculateMult(int a, int b) {
        return a * b;
    }

    public static int maxNumber(int a, int b) {
        int max = (a > b) ? a : b;
        return max;
    }

    public static int minNumber(int a, int b) {
        int min = (a < b) ? a : b;
        return min;
    }

}
