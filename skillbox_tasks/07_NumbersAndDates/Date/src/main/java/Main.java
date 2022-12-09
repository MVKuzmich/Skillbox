import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        int day = 1;
        int month = 1;
        int year = 1985;

        System.out.println(collectBirthdays(year, month, day));
    }

    public static String collectBirthdays(int year, int month, int day) {
        LocalDate birthday = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();
        String text = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.y - E").localizedBy(new Locale("us"));
        int i = 0;
        while (today.isAfter(birthday) || today.isEqual(birthday)) {
                text = text + i + " - " + formatter.format(birthday) + System.lineSeparator();
                birthday = birthday.plusYears(1);
                i++;
            }
                return text;
        }
    }




        //TODO реализуйте метод для построения строки в следующем виде
        //0 - 31.12.1990 - Mon
        //1 - 31.12.1991 - Tue
        

