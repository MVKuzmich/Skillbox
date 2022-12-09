package main.java;

public class Account {

    private long money;
    private String accNumber;
    private boolean isActive;

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
        isActive = true;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

