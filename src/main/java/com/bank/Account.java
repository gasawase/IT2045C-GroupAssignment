package com.bank;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Account implements Comparable<Account>{
    protected double balance;
    protected double interest;
    protected int periods;
    protected int accountNumber;
    protected double generatedInterest;

    //private static Map <String, Integer> ratePriorities = new HashMap<>();

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

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getGeneratedInterest() {
        return generatedInterest;
    }

    public void setGeneratedInterest(double generatedInterest) {
        this.generatedInterest = generatedInterest;
    }

    public int getPeriods() {
        return periods;
    }

    /**
     * Computes the balance after a number of periods.
     * Also calculates total generated interest, accessible via getGeneratedInterest()
     * @return the final balance.
     */
    public double compute() {
        for (int i = 0; i < periods; i++) {
            generatedInterest = generatedInterest + (balance * interest / 100);
            balance = balance + (balance * interest / 100);
        }
        return balance;
    }

    @Override
    public String toString(){
        return "Account Number: " + getAccountNumber() + " Balance: " + getBalance() + " Interest Rate: " + getInterest();
    }

    @Override
    public int compareTo(Account o) {
        int ourRate = (int)getInterest();
        int theirRate = (int)o.getInterest();
        return ourRate - theirRate;
    }
}
