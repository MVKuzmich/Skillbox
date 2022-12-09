package main.java;

public class Main {
    public static void main(String[] args) {
        Bank myBank = new Bank();
        Account first = new Account(20_000, "1");
        Account second = new Account(20_000, "2");
        Account third = new Account(20_000, "3");

        myBank.openAccount(first);
        myBank.openAccount(second);
        myBank.openAccount(third);


        System.out.println(myBank.getSumAllAccounts());

        try {
            new Thread(() -> {
                myBank.transfer("1", "2", 15_000);
            }).start();

            new Thread(() -> {
                myBank.transfer("1", "3", 5_000);
            }).start();
                new Thread(() -> {
                    myBank.transfer("2", "1", 10_000);
                }).start();
                new Thread(() -> {
                    myBank.transfer("2", "3", 10_000);
                }).start();
                new Thread(() -> {
                    myBank.transfer("3", "2", 10_000);
                }).start();
                new Thread(() -> {
                myBank.transfer("3", "1", 10_000);
                }).start();

                Thread.sleep(10000);


            } catch(Exception e){
                e.printStackTrace();
            }

            System.out.println(myBank.getBalance("1"));
            System.out.println(myBank.getBalance("2"));
            System.out.println(myBank.getBalance("3"));
            System.out.println(myBank.getSumAllAccounts());


        }
    }


