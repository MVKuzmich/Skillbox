public abstract class Client {

    protected double sumDepositCommission;
    protected double sumWithdrawalCommission;

    protected double moneyAmount = 0;

    protected double getAmount() {
        return moneyAmount;
    }

    public void put(double amountToPut) {
        moneyAmount += (amountToPut > 0) ? amountToPut - getDepositCommission(amountToPut) : 0;
    }

    public void take(double amountToTake) {
        moneyAmount -= (amountToTake + getWithdrawalCommission(amountToTake) <= moneyAmount)
                ? amountToTake + getWithdrawalCommission(amountToTake) : 0;
    }

    protected abstract double getWithdrawalCommission(double amount);

    protected abstract double getDepositCommission(double amount);

    protected abstract String getAccountInfo();

}
