package Employee;

import java.security.SecureRandom;
import java.util.*;

public class Company implements Comparable<Company> {

    private String companyName;
    private int personnelQuantity;
    private long companyIncome = 0L;

    public Company(String companyName) {
        this.companyName = companyName;
    }

    private List<Employee> employees = new ArrayList<>();

    public int getPersonnelCount() {
        return employees.size();
    }

    public void hire(Employee employee) {
        employees.add(employee);
    }


    public void hireAll(List<Employee> employee) {
        employees.addAll(employee);
    }


    public void fire(Employee employee) {
        employees.remove(employee);
    }

    public void fireAll(double minMonthSalary) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getMonthSalary() < minMonthSalary) {

                employees.remove(employees.get(i));

            }
        }
    }

    public void fireAll(int count) {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(employees.size());
           employees.remove(randomIndex);
        }
    }

    public double getIncome() {
        for (Employee object : employees) {
            if (object instanceof Manager) {
                companyIncome += ((Manager) object).getManagerProceeds();
            }
        }
        return companyIncome;

    }

    public List<Employee> getTopSalaryStaff(int count) {
        if (count > 0 && count <= employees.size()) {
            Comparator comparator = new EmployeeComparator();
            Collections.sort(employees, comparator);

            return employees.subList(0, count);
        }
        return new ArrayList<Employee>();
    }


    public List<Employee> getLowestSalaryStaff(int count) {
        Comparator comparator = new EmployeeComparatorReversed();
        Collections.sort (employees, comparator);
        return employees.subList(0, count);
    }

    @Override
    public int compareTo(Company company) {
        if (getIncome() > company.getIncome()) {
            return 1;
        }
        if (getIncome() < company.getIncome()) {
            return -1;
        }
        return 0;
    }
}



