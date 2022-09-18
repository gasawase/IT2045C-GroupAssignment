package com.bank;

public class Account {
    protected double balance;
    protected double interest;
    protected int periods;

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getInterest() {
        return interest;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public int getPeriods() {
        return periods;
    }

    public double compute() {
        // FIXME: wrong computation
        for (int i = 0; i < periods; i++) {
            balance = balance + (balance * interest / 100);
        }
        return balance;
    }

}
