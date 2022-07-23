public class IndividualBusinessman extends Client {

    private final double DEPOSIT_COMMISSION_RATE_ABOVE_LIMIT = 0.005;
    private final double DEPOSIT_COMMISSION_RATE_BELOW_LIMIT = 0.01;

    private final double DEPOSIT_LIMIT = 1_000.0;


    @Override
    protected double getWithdrawalCommission(double amountToTake) {
        return 0;
    }

    @Override
    protected double getDepositCommission(double amountToPut) {
        sumDepositCommission = (amountToPut >= DEPOSIT_LIMIT) ? amountToPut * DEPOSIT_COMMISSION_RATE_ABOVE_LIMIT :
        amountToPut * DEPOSIT_COMMISSION_RATE_BELOW_LIMIT;

        return sumDepositCommission;
    }

    @Override
    protected String getAccountInfo() {
        return "Информация о счете: " +
                "Баланс счета равен " + getAmount() + "рублей" +
                "Комиссия за пополнение счета: " +
                "при сумме пополнения менее " + DEPOSIT_LIMIT + "рублей - комиссиия " +
                DEPOSIT_COMMISSION_RATE_BELOW_LIMIT * 100 + "процент (а, ов);" +
                "при сумме пополнения менее " + DEPOSIT_LIMIT + "рублей - комиссиия " +
                DEPOSIT_COMMISSION_RATE_ABOVE_LIMIT * 100 + "процент (а, ов);" +
                "Комиссия за снятие средств со счета - не установлена";
    }
}
