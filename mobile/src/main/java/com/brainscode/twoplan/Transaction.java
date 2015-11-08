package com.brainscode.twoplan;

import java.io.Serializable;

/**
 * Created by khomenkos on 08/11/15.
 */
public class Transaction implements Serializable {
    String description;
    String currency;

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

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
