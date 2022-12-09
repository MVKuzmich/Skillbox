public class LegalPerson extends Client {

    private final double WITHDRAWAL_COMMISSION_RATE = 0.01;

    @Override
    protected double getWithdrawalCommission(double amountToTake) {
        sumWithdrawalCommission = amountToTake * WITHDRAWAL_COMMISSION_RATE;
        return sumWithdrawalCommission;
    }

    @Override
    protected double getDepositCommission(double amountToPut) {
        return 0;
    }

    @Override
    protected String getAccountInfo() {
        return "Информация о счете: " +
                "Баланс счета равен " + getAmount() + "рублей" +
                "Комиссия за пополнение счета - не применяется" +
                "Комиссия за снятие средств со счета - " + WITHDRAWAL_COMMISSION_RATE * 100 + "процент(а, ов)";
    }
}
