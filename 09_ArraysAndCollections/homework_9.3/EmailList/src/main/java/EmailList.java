import javax.swing.event.TreeExpansionListener;
import java.util.*;

public class EmailList {
    TreeSet<String> emailList = new TreeSet<>();

       public void add(String email) {
        String regexForEmail = "[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        if (email.matches(regexForEmail)) {
            emailList.add(email.toLowerCase());
            // валидный формат email добавляется
        }
    }
    public List<String> getSortedEmails() {
        List<String> emailListFromSet = new ArrayList<>();
        emailListFromSet.addAll(emailList);
        return emailListFromSet;
    }

        // возвращается список электронных адресов в алфавитном порядке

    }


