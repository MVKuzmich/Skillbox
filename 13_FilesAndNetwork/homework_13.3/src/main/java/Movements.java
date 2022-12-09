import net.sf.saxon.expr.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movements {

    List<String> parseOperationList;


    public Movements(String pathMovementsCsv) {
        try {
            parseOperationList = Files.readAllLines(Paths.get(pathMovementsCsv));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BankOperation> getBankOperationStatement() throws ParseException {
        List<BankOperation> bankOperationStatement = new ArrayList<>();

        for (String operation : parseOperationList) {
            if(operation.contains("Дата операции")) {
                continue;
            }
            if (operation.contains("\"")) {
                String regexForOperationWithQuotes = "\"[0-9]+,[0-9]+\"";
                String operPartTillQuotes = "";
                String operPartInQuotes = "";
                String operPartAfterQuotes = "";

                Pattern pattern = Pattern.compile(regexForOperationWithQuotes);
                Matcher matcher = pattern.matcher(operation);
                while (matcher.find()) {
                    operPartTillQuotes = operation.substring(0, matcher.start());
                    operPartInQuotes = operation.substring(matcher.start(), matcher.end());
                    operPartAfterQuotes = operation.substring(matcher.end());

                }
                String operPartInQuotesFormat = operPartInQuotes.replaceAll("\"", "").replace(',', '.');
                operation = operPartTillQuotes.concat(operPartInQuotesFormat).concat(operPartAfterQuotes);
            }
                String[] operationElements = operation.split(",");
            if(operationElements.length != 8) {
                System.out.println("Wrong operation " + operation);
                continue;
           }

            int start = operationElements[5].indexOf("1028") + "1028".length();
            String operationDescription = "";
            String regexEnd = "[0-9]{2}.[0-9]{2}.[0-9]{2} [0-9]{2}.[0-9]{2}.[0-9]{2}";
            Pattern pattern = Pattern.compile(regexEnd);
            Matcher matcher = pattern.matcher(operationElements[5]);
            while(matcher.find()) {
                int end = matcher.start();
                operationDescription = operationElements[5].substring(start, end).trim();
            }

            bankOperationStatement.add(new BankOperation(operationDescription,
                            LocalDate.parse(operationElements[3], DateTimeFormatter.ofPattern("dd.MM.yy")),
                            Double.parseDouble(operationElements[6]),
                            Double.parseDouble(operationElements[7])
                    ));

        }
        return bankOperationStatement;
    }

        public double getExpenseSum() {
            double expenseSum = 0.0;
            try {
                List<BankOperation> bankList = getBankOperationStatement();
                expenseSum = bankList.stream().mapToDouble(BankOperation::getSumExpense).sum();

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return expenseSum;
        }

        public double getIncomeSum() {
            double incomeSum = 0.0;
            try {
                List<BankOperation> bankList = getBankOperationStatement();
                incomeSum = bankList.stream().mapToDouble(BankOperation::getSumIncome).sum();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return incomeSum;

        }
    }

