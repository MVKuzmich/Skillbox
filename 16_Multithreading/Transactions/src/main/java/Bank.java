package main.java;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Bank {

    private Map<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    private final long transferLimitToCheck = 50_000;

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        try {
                    Account fromAccount = getAccount(fromAccountNum);
                    Account toAccount = getAccount(toAccountNum);
            synchronized (fromAccountNum.compareTo(toAccountNum) > 0 ? fromAccount : toAccount) {
                synchronized (fromAccountNum.compareTo(toAccountNum) > 0 ? toAccount : fromAccount) {
                    if (fromAccount.getMoney() < amount) {
                        throw new Exception("NOT ENOUGH FUNDS!");
                    }
                    if (amount > transferLimitToCheck) {
                        boolean isCheck = isFraud(fromAccountNum, toAccountNum, amount);
                        if (isCheck) {
                            fromAccount.setActive(false);
                            toAccount.setActive(false);
                        }
                    }
                    if (fromAccount.isActive() || toAccount.isActive()) {
                        fromAccount.setMoney(fromAccount.getMoney() - amount);
                        toAccount.setMoney(toAccount.getMoney() + amount);

                    } else {
                        System.out.println("Account " + fromAccount.getAccNumber() + " is blocked");
                        System.out.println("Account " + toAccount.getAccNumber() + " is blocked");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * реализовать метод. Возвращает остаток на счёте.
     */
    public synchronized long getBalance(String accountNum) {

        return getAccount(accountNum).getMoney();
    }

    public void openAccount(Account account) {
        accounts.put(account.getAccNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public long getSumAllAccounts() {

        return accounts.values().stream().mapToLong(Account::getMoney).sum();
    }

}
