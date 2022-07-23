public class Main {

    public static void main(String[] args) {
        Basket basket1 = new Basket();
        basket1.add("Milk", 40, 2, 2000);
        basket1.print("Milk");

        Basket basket2 = new Basket();
        basket2.add("Sugar", 100, 1, 1000);
        basket2.print("Sugar");

        Basket basket3 = new Basket();
        basket3.add("Newspaper", 20, 5);
        basket3.print("Newspaper");
// Выполнение методов класса Arithmetic
        System.out.println(Arithmetic.calculateSum(5, 4));
        System.out.println(Arithmetic.calculateMult(5, 4));
        System.out.println(Arithmetic.maxNumber(5, 4));
        System.out.println(Arithmetic.minNumber(5, 4));

// Выполнение методов класса Printer

        Printer printer1 = new Printer();
        printer1.appendDoc("Летом теплo", "Лето", 5);
        printer1.appendDoc("Зимой холодно");
        printer1.appendDoc("Трава зеленая", "Природа");
        printer1.appendDoc("Небо голубое", "Космос", 20);
        printer1.print();
        System.out.println("Количество документов в очереди печати - " + printer1.getPendingPagesCount());

        Printer printer2 = new Printer();
        printer2.appendDoc("Летом теплo", "Лето", 11);
        printer2.appendDoc("Зимой холодно");
        printer2.appendDoc("Трава зеленая", "Природа");
        printer2.appendDoc("Небо голубое", "Космос", 35);
        printer2.print();
        System.out.println("Количество документов в очереди печати - " + printer2.getPendingPagesCount());






    }


}


