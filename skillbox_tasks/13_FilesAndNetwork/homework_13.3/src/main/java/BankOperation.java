import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class BankOperation implements Comparator<BankOperation> {

    private String description;
    private LocalDate date;
    private double sumExpense;
    private double sumIncome;

    public BankOperation(String description, LocalDate date, double sumIncome, double sumExpense) {
        this.description = description;
        this.date = date;
        this.sumIncome = sumIncome;
        this.sumExpense = sumExpense;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getSumExpense() {
        return sumExpense;
    }

    public double getSumIncome() {
        return sumIncome;
    }

    public String toString() {
        if (sumIncome == 0) {
            return "Expense operation " + description + " - " + sumExpense;
        } else {
            return "Income operation " + description + " - " + sumIncome;
        }
    }

    @Override
    public int compare(BankOperation o1, BankOperation o2) {
        return o1.getDescription().compareTo(o2.getDescription());
    }

}
