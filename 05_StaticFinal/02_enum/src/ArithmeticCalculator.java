public class ArithmeticCalculator {
    public int a;
    public int b;

    public ArithmeticCalculator(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int calculate(Operation operation) {
        int result = 0;
        switch (operation) {
            case ADD:
                result = a + b;
                break;
            case SUBTRACT:
                result = a - b;
                break;
            case MULTIPLY:
                result = a * b;
                break;
            default:
                result = -1;
        }
        return result;
    }
}