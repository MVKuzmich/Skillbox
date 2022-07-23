package Employee;

import java.util.List;

public interface Employee {

    double getMonthSalary();

    List<Employee> addInListForHire(Employee employee, int count);
}
