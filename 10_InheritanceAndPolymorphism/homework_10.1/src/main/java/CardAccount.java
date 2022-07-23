public class CardAccount extends BankAccount {

    private final double COMMISSION_RATE = 0.01;

    public boolean take(double amountToTake) {
        if((1 + COMMISSION_RATE) * amountToTake <= moneyAmount) {
            moneyAmount -= ((1 + COMMISSION_RATE) * amountToTake);
            return true;
        } else {
            moneyAmount -= 0;
            return false;
        }
    }
}
