import java.util.*;

public class PhoneBook {

    private final Map<String, Set<String>> phoneBook = new TreeMap<>();

    public void addContact(String phone, String name) {

        if (checkValidName(name) && checkValidPhone(phone)) {
            // сначала проверим - есть ли такой телефон в нашей книге?
            // это самый сложынй кейс - потому что нам нужно перезаписать контакт
            for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
                if (entry.getValue().contains(phone) && !entry.getKey().equals(name)) {
                    entry.getValue().remove(phone);
                    if (entry.getValue().isEmpty()) { // удаляем контакт, если у него не осталось номеров
                        phoneBook.remove(entry.getKey());
                    }
                    break;
                }
            }
            // номер (если он ранее существовал) - удален. Можно дальше продолжат обработку.

            if (!phoneBook.containsKey(name)) {
                phoneBook.put(name, new HashSet<>());
            }
            phoneBook.get(name).add(phone);

            // проверьте корректность формата имени и телефона (отдельные методы для проверки)
            // если такой номер уже есть в списке, то перезаписать имя абонента
        }
    }

    public String getContactByPhone(String phone) {
        String contactByPhone = "";
        for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
            String key = entry.getKey(); // получение ключа
            Set<String> value = entry.getValue(); // получения ключа
            if (value.contains(phone)) {
                contactByPhone = key + " - " + formatPhone(value);
                // формат одного контакта "Имя - Телефон"
                // если контакт не найдены - вернуть пустую строку
            }
        }
        return contactByPhone;
    }

    public Set<String> getContactByName(String name) {
        Set<String> contactByName = new TreeSet<>();
        String contact = "";
        if (phoneBook.containsKey(name)) {
            contact = name + " - " + formatPhone(phoneBook.get(name));
            contactByName.add(contact);
        }
        // формат одного контакта "Имя - Телефон"
        // если контакт не найден - вернуть пустой TreeSet
        return contactByName;
    }

    public Set<String> getAllContacts() {
        Set<String> allContacts = new TreeSet<>();
        if (!phoneBook.isEmpty()) {
            for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
                allContacts.add(entry.getKey() + " - " + formatPhone(entry.getValue()));
            }
            // формат одного контакта "Имя - Телефон"
            // если контактов нет в телефонной книге - вернуть пустой TreeSet

        }
        return allContacts;
    }

    public boolean checkValidPhone(String phone) {
        String regexForPhone = "7[0-9]{10}";
        return phone.matches(regexForPhone);
    }

    public boolean checkValidName(String name) {
        String regexForName = "[А-я]+";
        return name.matches(regexForName);
    }

    public String formatPhone(Set<String> phones) {
        return String.join(", ", phones);
    }
}


// для обхода Map используйте получение пары ключ->значение Map.Entry<String,String>
// это поможет вам найти все ключи (key) по значению (value)
    /*
        for (Map.Entry<String, String> entry : map.entrySet()){
            String key = entry.getKey(); // получения ключа
            String value = entry.getValue(); // получения ключа
        }
    */
