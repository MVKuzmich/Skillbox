package Employee;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Employee {

    private Company company;


    private static int managerCounter = 1;
    String personnelNumber;

    private long managerProceeds;
    private double monthSalary;

    public Manager(Company company) {
        this.company = company;
        managerProceeds = (Math.round(MIN_MANAGER_PROCEEDS + Math.random() * (MAX_MANAGER_PROCEEDS - MIN_MANAGER_PROCEEDS))
                * 100) / 100;
        monthSalary = FIX_MANAGER_SALARY + MANAGER_BONUS_FROM_PROCEEDS * getManagerProceeds();
        personnelNumber = String.format("%s%05d", "M", managerCounter);
        managerCounter++;

    }
    private final double FIX_MANAGER_SALARY = 25_000.0;
    private final double MANAGER_BONUS_FROM_PROCEEDS = 0.05;

    private final double MIN_MANAGER_PROCEEDS = 115_000.0;
    private final double MAX_MANAGER_PROCEEDS = 140_000.0;


    @Override
    public double getMonthSalary() {

        return monthSalary;
    }

    @Override
    public List<Employee> addInListForHire(Employee employee, int count) {
        List<Employee> listForFire = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            listForFire.add(new Manager(company));
        }
        return listForFire;
    }

    public double getManagerProceeds() {
        return managerProceeds;
    }

    public String toString() {
        return monthSalary + " - " +
                personnelNumber;
    }

}
