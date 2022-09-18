package com.bank;

public class Checking extends Account {
    final int FEE = 5;
    
    @Override
    /**
     * Computes the balance after a number of periods,
     * includes a set FEE each period.
     * @return the final balance.
     */
    public double compute() {
        for (int i = 0; i < periods; i++) {
            balance = balance + (balance * interest / 100) - FEE;
        }
        return balance;
    }
}
