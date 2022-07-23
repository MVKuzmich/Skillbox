package Employee;

import Employee.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operator implements Employee {

    private Company company;

    private double monthSalary;
    private final double MIN_OPERATOR_SALARY = 20_000.0;
    private final double MAX_OPERATOR_SALARY = 30_000.0;

    private static int operatorCounter = 1;
    private String personnelNumber;

    public Operator(Company company) {
        this.company = company;
        monthSalary = (Math.round(MIN_OPERATOR_SALARY + Math.random() * (MAX_OPERATOR_SALARY - MIN_OPERATOR_SALARY)) * 100) / 100;
        personnelNumber = String.format("%s%05d", "O", operatorCounter);
        operatorCounter++;
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
            listForFire.add(new Operator(company));
        }
        return listForFire;
    }

    public String toString() {
        return monthSalary + " - " +
                personnelNumber;
    }
}
