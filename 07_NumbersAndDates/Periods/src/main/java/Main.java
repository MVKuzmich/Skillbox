import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {

  public static void main(String[] args) {
    LocalDate birthday = LocalDate.of(1995, 5, 23);
    System.out.print(getPeriodFromBirthday(birthday));

  }

  public static String getPeriodFromBirthday(LocalDate birthday) {
    LocalDate today = LocalDate.now();
    long difference = birthday.until(today, ChronoUnit.DAYS);
    String text = difference / 365 + " years, " + (difference % 365) / 30 + " months, " +
            (difference % 365) % 30 + " days";
    return text;
  }

}
