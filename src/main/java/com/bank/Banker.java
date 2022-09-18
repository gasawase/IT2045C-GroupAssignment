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
            // What type of account to create: Checking, Savings, or Certificate of Deposit.
            String[] accountTypes = {SAVINGS, CHECKING, CERT_OF_DEPOSIT};
            Object accountObject = JOptionPane.showInputDialog(null, "Choose a type of account to create", "Create an Account", JOptionPane.INFORMATION_MESSAGE, null, accountTypes, accountTypes[0]);
            
            Account account = createAccount(accountObject);

            // For all accounts, prompt the user for beginning balance, interest rate (as a whole number), and number of periods.
            String strBalanceStart = JOptionPane.showInputDialog("Enter starting balance:");
            double doubleBalanceStart = Double.parseDouble(strBalanceStart);
            account.setBalance(doubleBalanceStart);

            String strInterestRate = JOptionPane.showInputDialog("Enter interest rate:");
            Double doubleInterestRate = Double.parseDouble(strInterestRate);
            account.setInterest(doubleInterestRate);

            String strPeriods = JOptionPane.showInputDialog("Enter number of periods:");
            int intPeriods = Integer.parseInt(strPeriods);
            account.setPeriods(intPeriods);

            // If the user selects Certificate of Deposit, prompt for term (length to maturity).
            if (account instanceof CertificateOfDeposit) {
                String strTerms = JOptionPane.showInputDialog("Enter number of terms:");
                int intTerms = Integer.parseInt(strTerms);
                ((CertificateOfDeposit) account).setMaturity(intTerms);
            }

            // Add each account to an ArrayList<Account>
            allAccounts.add(account);

            // Ask the user if he/she wants to open another account, using a JOptionPane.showConfirmDialog().
            // If the user chooses "Yes", prompt for another account.  If the user chooses "No", stop prompting for new accounts.
            doBanking = JOptionPane.showConfirmDialog(null, "Do you want to create another account?",
                "Create Another?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        } while (doBanking == JOptionPane.YES_OPTION); 
    }

    public static void displayBalance() {
        JOptionPane.showMessageDialog(null, "testing");
        do {
            for (Account account : allAccounts) {
                JOptionPane.showMessageDialog(null, "New Balance: " + account.compute());
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
}
