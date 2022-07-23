package Employee;

import Employee.Employee;

import java.util.Comparator;

public class EmployeeComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        return (int) ((int) o2.getMonthSalary() - o1.getMonthSalary());
    }
}