public class BankAccount {

  protected double moneyAmount = 0;

  public double getAmount() {
    return moneyAmount;
  }

  public void put(double amountToPut) {
   moneyAmount += (amountToPut > 0) ? amountToPut : 0;
  }

  public boolean take(double amountToTake) {
    if (amountToTake <= moneyAmount) {
      moneyAmount -= amountToTake;
      return true;
    } else {
      return false;
    }
  }
  public boolean send(BankAccount receiver, double amount) {
    if (take(amount)) {
      receiver.put(amount);
      return true;
    } else {
      return false;
    }
  }
}

