package com.bank;

public class Checking extends Account {
    final int FEE = 5;
    
    @Override
    public double compute() {
        for (int i = 0; i < periods; i++) {
            balance = balance + (balance * interest / 100) - FEE;
        }
        return balance;
    }
}
