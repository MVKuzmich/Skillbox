public class Basket {
        private static int basketsTotalValue = 0; // переменная Общая стоимость всех корзин
        private static int basketsTotalCountItems = 0; // переменная Общее количество товаров в корзинах
        private static int count = 0;
        private String items = "";
        private static int totalPrice = 0;
        private int limit;
        private double totalWeight = 0;
        private static int totalCountItems = 0;
        public static int averagePricePerBasketsItem;
        public static int averageOneBasketValue;

        public static void increaseBasketTotalValue () {
            basketsTotalValue = basketsTotalValue + totalPrice; // метод, увеличивает Общую стоимость всех корзин
        }
        public static void increaseBasketTotalCountItems() {
            basketsTotalCountItems = basketsTotalCountItems + totalCountItems ; // метод, увеличивает Общее количество товаров в корзинах
        }
        public static int calculateAveragePricePerBasketsItem () {
            averagePricePerBasketsItem = basketsTotalValue / basketsTotalCountItems;
            return averagePricePerBasketsItem; // метод, рассчитывает и возвращает Среднюю цену товара во всех корзинах
        }
        public static void calculateAverageOneBasketValue() {
            averageOneBasketValue = basketsTotalValue / count; // метод, рассчитывает Среднюю стоимость одной корзины

        }
        public Basket() {
            count = count + 1;
            items = "Список товаров:";
            this.limit = 1000000;
        }

        public Basket(int limit) {
            this();
            this.limit = limit;
        }

        public Basket(String items, int totalPrice, double totalWeight, int totalCountItems) {
            this();
            this.items = this.items + items;
            this.totalPrice = totalPrice;
            this.totalWeight = totalWeight;
            this.totalCountItems = totalCountItems;

        }

        public static int getCount() {
            return count;
        }

        public void add(String name, int price) {
            add(name, price, 1, 0);
        }

        public void add(String name, int price, int countItems) {add(name, price, countItems, 0); }

        public void add(String name, int price, int countItems, double weight) {
            boolean error = false;
            if (contains(name)) {
                error = true;
            }

            if (totalPrice + countItems * price >= limit) {
                error = true;
            }

            if (error) {
                System.out.println("Error occured :(");
                return;
            }

            items = items + "\n" + name + " - " +
                    countItems + " шт. - " + price + " руб. -  " + weight + " г. ";
            totalPrice = totalPrice + countItems * price;
            totalWeight = totalWeight + weight;
            totalCountItems = totalCountItems + countItems;

        }

        public void clear() {
            items = "";
            totalPrice = 0;
            totalWeight = 0;
            totalCountItems = 0;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public double getTotalWeight () { return totalWeight; }

        public int getTotalCountItems () { return totalCountItems; }

        public boolean contains(String name) {
            return items.contains(name);
        }

        public void print(String title) {
            System.out.println(title);
            if (items.isEmpty()) {
                System.out.println("Корзина пуста");
            } else {
                System.out.println(items);
            }
        }
}
