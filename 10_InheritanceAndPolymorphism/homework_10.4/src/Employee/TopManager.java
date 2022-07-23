package Employee;

import Employee.Employee;

import java.util.ArrayList;
import java.util.List;

public class TopManager implements Employee {

    private Company company;
    private double monthSalary;

    private final double FIX_TOP_MANAGER_SALARY = 150_000.0;
    private final double TOP_MANAGER_BONUS_FROM_SALARY = 1.5;
    private final double MIN_COMPANY_INCOME_FOR_BONUS = 10_000_000;

    private static int topManagerCounter = 1;
    String personnelNumber;

    public TopManager(Company company) {
        this.company = company;
        personnelNumber = String.format("%s%05d", "TM", topManagerCounter);
        topManagerCounter++;
        monthSalary = (company.getIncome() > MIN_COMPANY_INCOME_FOR_BONUS) ?
                (1 + TOP_MANAGER_BONUS_FROM_SALARY) * FIX_TOP_MANAGER_SALARY : FIX_TOP_MANAGER_SALARY;
    }
    public String getPersonnelNumber() {

        return personnelNumber;
    }

    @Override
    public double getMonthSalary() {

        return monthSalary;
    }

    @Override
    public List<Employee> addInListForHire(Employee employee, int count) {
        List<Employee> listForFire = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            listForFire.add(new TopManager(company));
        }
        return listForFire;
    }

    public String toString() {
        return monthSalary + " - " +
                personnelNumber;
    }
}
