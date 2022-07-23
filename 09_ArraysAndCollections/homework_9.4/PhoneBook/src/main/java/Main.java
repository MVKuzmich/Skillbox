import java.util.Scanner;

public class Main {
    public static PhoneBook phoneBook = new PhoneBook();

    private static String name;
    private static String phone;

    public static void main(String[] args) {
        while (true) {
            System.out.println("Введите номер, имя или команду");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }

         if (input.equals("LIST")) {
                System.out.println(phoneBook.getAllContacts());
                continue;
            } else if (phoneBook.checkValidName(input)) {
                    name = input;

                if (!phoneBook.getContactByName(input).isEmpty()) {
                    System.out.println(phoneBook.getContactByName(input));

                } else if (!phoneBook.getContactByPhone(phone).isEmpty()) {
                    phoneBook.addContact(phone, input);
                    System.out.println("Контакт перезаписан с новым именем");
                    phone = null;

                } else if (phone != null) {
                    phoneBook.addContact(phone, name);
                    System.out.println("Контакт сохранен!");
                    name = null;
                    phone = null;

                } else {
                    System.out.println("Такого имени в телефонной книге нет");
                    System.out.println("Введите номер телефона для абонента " + "\"" + name + "\"");
                }
                continue;

            } else if (phoneBook.checkValidPhone(input)) {
                    phone = input;

                    if (!phoneBook.getContactByPhone(input).isEmpty()) {
                        System.out.println(phoneBook.getContactByPhone(input));

                    } else if (!phoneBook.getContactByName(name).isEmpty()) {
                        phoneBook.addContact(input, name);
                        System.out.println("Дополнительный номер телефона записан");
                        name = null;

                    } else if (name != null) {
                        phoneBook.addContact(phone, name);
                        System.out.println("Контакт сохранен!");
                        name = null;
                        phone = null;

                    } else {
                        System.out.println("Такого номера нет в телефонной книге");
                        System.out.println("Введите имя абонента для номера " + "\"" + phone + "\"");
                    }
                continue;

            } else {
                System.out.println("Неверный формат ввода");

            }
        }
    }
}









