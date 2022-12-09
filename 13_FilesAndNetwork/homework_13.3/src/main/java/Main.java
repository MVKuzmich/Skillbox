import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static final String pathMovementsCsv = "src/test/resources/movementList.csv";

    static final Movements bankMovements = new Movements(pathMovementsCsv);


    public static void main(String[] args) throws Exception {
        List<BankOperation> bankStatement = bankMovements.getBankOperationStatement();

        System.out.println("Сумма расходов: " + bankMovements.getExpenseSum() + " рублей");
        System.out.println("Сумма доходов: " + bankMovements.getIncomeSum() + " рублей");

        List<BankOperation> expenseBankStatement = bankStatement.stream().filter(op -> op.getSumExpense() != 0).
                sorted(Comparator.comparing(BankOperation::getDescription)).collect(Collectors.toList());

        System.out.println("Суммы расходов по оранизациям: ");
        String previousOperDescription = "";
        double sumEqualExpenseOperation = 0.0;
        for (BankOperation op : expenseBankStatement) {
            String currentOperDescription = op.getDescription();
            if (previousOperDescription.isBlank()) {
                sumEqualExpenseOperation += op.getSumExpense();
                previousOperDescription = currentOperDescription;
                continue;
            }

            if (currentOperDescription.equals(previousOperDescription)) {
                sumEqualExpenseOperation += op.getSumExpense();
                previousOperDescription = currentOperDescription;
                continue;
            } else {

                System.out.println(previousOperDescription + " - " + sumEqualExpenseOperation + " рублей");
                previousOperDescription = currentOperDescription;
                sumEqualExpenseOperation = op.getSumExpense();

            }
        }
    }
}








