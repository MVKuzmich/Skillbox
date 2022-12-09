import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    private String regexForName = "[А-я]+\\s+[А-я]+";
    private String regexForPhone = "\\+79[0-9]{9}";
    private String regexForEmail = "[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new IllegalArgumentException("Wrong input format. Correct format:" +
                    "add Василий Петров vasily.petrov@gmail.com +79215637722)");
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];

        if (!name.matches(regexForName)) {
            throw new IllegalArgumentException("Wrong name input. Correct format:" +
                    "(sample) Василий Петров");
        }

        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
        if (!components[INDEX_PHONE].matches(regexForPhone) || !components[INDEX_EMAIL].matches(regexForEmail)) {
            throw new IllegalArgumentException("Wrong format phone number or Email. Correct format:" +
                    "(sample) vasily.petrov@gmail.com +79215637722");
        }
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
        if(!name.matches(regexForName)) {
            throw new IllegalArgumentException("Wrong name input! Correct format: (sample) Василий Петров");
        }
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}