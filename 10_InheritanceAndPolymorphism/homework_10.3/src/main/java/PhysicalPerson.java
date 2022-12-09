public class PhysicalPerson extends Client {

    @Override
    protected double getWithdrawalCommission(double amount) {
        return 0;
    }

    @Override
    protected double getDepositCommission(double amount) {
        return 0;
    }

    @Override
    protected String getAccountInfo() {
        return "Информация о счете: " +
                "Баланс счета равен " + getAmount() + "рублей" +
                "Комиссия за пополнение счета - не применяется" +
                "Комиссия за снятие средств со счета - не применяется";

    }


}
