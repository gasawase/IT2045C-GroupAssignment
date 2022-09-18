package com.bank;

import javax.swing.*;
import java.util.ArrayList;


public class Banker {

    public static final String SAVINGS = "Savings";
    public static final String CHECKING = "Checking";
    public static final String CERT_OF_DEPOSIT = "Certificate of Deposit";

    private static ArrayList<Account> allAccounts = new ArrayList<>();
    public static void main(String[] args){        
        promptUser();
        displayBalance();
    }

    public static void promptUser(){
        
        int doBanking = JOptionPane.NO_OPTION;
        do {
            String[] accountTypes = {SAVINGS, CHECKING, CERT_OF_DEPOSIT};
            Object accountObject = JOptionPane.showInputDialog(null, "Choose a type of account to create", "Create an Account", JOptionPane.INFORMATION_MESSAGE, null, accountTypes, accountTypes[0]);
            
            Account account = createAccount(accountObject);

            String strBalanceStart = JOptionPane.showInputDialog("Enter starting balance:");
            double doubleBalanceStart = Double.parseDouble(strBalanceStart);
            account.setBalance(doubleBalanceStart);

            String strInterestRate = JOptionPane.showInputDialog("Enter interest rate:");
            Double doubleInterestRate = Double.parseDouble(strInterestRate);
            account.setInterest(doubleInterestRate);

            String strPeriods = JOptionPane.showInputDialog("Enter number of periods:");
            int intPeriods = Integer.parseInt(strPeriods);
            account.setPeriods(intPeriods);

            if (account instanceof CertificateOfDeposit) {
                String strTerms = JOptionPane.showInputDialog("Enter number of terms:");
                int intTerms = Integer.parseInt(strTerms);
                ((CertificateOfDeposit) account).setMaturity(intTerms);
            }

            allAccounts.add(account);

            doBanking = JOptionPane.showConfirmDialog(null, "Do you want to create another account?",
                "Create Another?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        } while (doBanking == JOptionPane.YES_OPTION); 
    }

    public static void displayBalance() {
        do {
            for (Account account : allAccounts) {
                String message = "In your " + accountTypeString(account) + " account...\nNew Balance: " + account.compute();
                JOptionPane.showMessageDialog(null, message);
            }
        } while (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Do you want to continue calculations?", "Calculate Again?",
             JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE));
    }

    private static Account createAccount(final Object chosenAccount) {
        Account account = new Account();
        if (chosenAccount.toString().equals(SAVINGS)) {
            account = new Savings();
        } else if (chosenAccount.toString().equals(CHECKING)) {
            account = new Checking();
        } else if (chosenAccount.toString().equals(CERT_OF_DEPOSIT)) {
            account = new CertificateOfDeposit();
        }
        
        return account;
    }

    /**
     * Simple method to return the type of account as a string
     * @param account the account being checked
     * @return a string of the account type. defaults to "bank"
     */

    private static String accountTypeString(Account account){
        if (account instanceof Savings) {
            return SAVINGS;
        } else if (account instanceof Checking) {
            return CHECKING;
        } else if (account instanceof CertificateOfDeposit) {
            return CERT_OF_DEPOSIT;
        } else {
            return "bank";
        }
    }
}
