import Employee.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Company myCompany = new Company("Аврора");

        // Найм сотрудников
        List<Employee> listForHire = new ArrayList<>();

        // Менеджеры 80 человек

        for (int i = 0; i < 80; i++) {
            listForHire.add(new Manager(myCompany));
        }
        // Топ-менеджеры 10
        for (int i = 0; i < 10; i++) {
            listForHire.add(new TopManager(myCompany));
        }
        // Операторы 180
        for (int i = 0; i < 180; i++) {
            listForHire.add(new Operator(myCompany));
        }

            myCompany.hireAll(listForHire);

        System.out.println("Сумма дохода компании - " + BigInteger.valueOf((long) myCompany.getIncome()));
        System.out.println("Количество сотрудников после найма: " + myCompany.getPersonnelCount());

        System.out.println("Список низких зарплат: " + System.lineSeparator() +
                myCompany.getLowestSalaryStaff(30));
        System.out.println("Список высоких зарплат: " + System.lineSeparator() +
                myCompany.getTopSalaryStaff(15));

        // Увольнение сотрудников

        myCompany.fireAll(135);

        System.out.println("Количество сотрудников после увольнения: " + myCompany.getPersonnelCount());
        System.out.println("Сумма дохода компании после увольнения - " + BigInteger.valueOf((long)myCompany.getIncome()));

        System.out.println("Список низких зарплат: " + System.lineSeparator() +
                myCompany.getLowestSalaryStaff(30));
        System.out.println("Список высоких зарплат: " + System.lineSeparator() +
                myCompany.getTopSalaryStaff(15));
    }


}
