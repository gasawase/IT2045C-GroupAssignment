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

    /**
     * Computes the balance after a number of periods.
     * @return the final balance.
     */
    public double compute() {
        for (int i = 0; i < periods; i++) {
            balance = balance + (balance * interest / 100);
        }
        return balance;
    }

    @Override
    public String toString(){
        return "Balance: " + getBalance() + " Interest Rate: " + getInterest();
    }

}
