package Employee;

import Employee.Employee;

import java.util.Comparator;

public class EmployeeComparatorReversed implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
          return (int) ((int) o1.getMonthSalary() - o2.getMonthSalary());
        }
}