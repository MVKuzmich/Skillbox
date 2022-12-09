import java.time.LocalDate;

public class DepositAccount extends BankAccount {

    LocalDate lastIncome;

    public void put(double amountToPut) {
        moneyAmount += (amountToPut > 0) ? amountToPut : 0;
        lastIncome = LocalDate.now();
    }

    public boolean take(double amountToTake) {
        LocalDate dayToTake = LocalDate.now();
        if (amountToTake <= moneyAmount && dayToTake.isAfter(lastIncome.plusDays(30))) {
            moneyAmount -= amountToTake;
            return true;
        } else {
            moneyAmount -= 0;
            return false;
        }
    }


}






