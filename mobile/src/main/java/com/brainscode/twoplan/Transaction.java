package com.brainscode.twoplan;

/**
 * Created by khomenkos on 08/11/15.
 */
public class Transaction {
    String description;
    String currency;
    double amount;

    public Transaction(double amount, String description, String currency) {
        this.amount = amount;
        this.description = description;
        this.currency = currency;
    }




    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
