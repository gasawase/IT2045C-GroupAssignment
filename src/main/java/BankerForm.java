import com.bank.Account;
import com.bank.AccountSerializer;
import com.bank.Banker;
import com.bank.CertificateOfDeposit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;


public class BankerForm {
    private JPanel pnlMain;
    private JPanel pnlCenterMain;
    private JPanel pnlInnerNorth;
    private JPanel pnlInnerCenter;
    private JPanel jplButtonBar;
    private JButton btnAddAccount;
    private JButton btnCompute;
    private JTextField txtPeriods;
    private JList<Account> lstAccounts;
    private JComboBox<String> cmbAccountType;
    private JTextField txtInterestRate;
    private JTextField txtTerms;
    private JLabel lblAccountType;
    private JLabel lblInterestRate;
    private JLabel lblStartBalance;
    private JTextField txtStartBalance;
    private JLabel lblTerms;
    private JTextField txtImportFileName;
    private JLabel lblImports;
    private JButton btnImportFile;
    private JTextField txtAccountNumber;
    private JLabel lblAccountNumber;
    private JButton btnClearInterest;
    private JButton btnInterestReport;
    private JButton btnWithdraw;

    public Vector<Account> allAccounts = new Vector<Account>();
    public static Queue<Account> accountQueue = new PriorityQueue<Account>();

    public BankerForm() {

        initializeAccountTypeComboBox();

        lstAccounts.setListData(allAccounts);

        btnAddAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateInputs()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all input forms before adding an account.");
                    return;
                }
                String strAccountNumber = txtAccountNumber.getText();
                int accountNumber = Integer.parseInt(strAccountNumber);

                String strBalance = txtStartBalance.getText();
                double balance = Double.parseDouble(strBalance);

                String strInterestRate = txtInterestRate.getText();
                double interestRate = Double.parseDouble(strInterestRate);

                String type = cmbAccountType.getSelectedItem().toString();
                Account account = Banker.createAccount(type);
                account.setBalance(balance);
                account.setInterest(interestRate);
                account.setAccountNumber(accountNumber);

                if (cmbAccountType.getSelectedItem().toString().equals(Banker.CERT_OF_DEPOSIT)) {
                    if (account instanceof CertificateOfDeposit) {
                        String strTerms = txtTerms.getText();
                        int terms = Integer.parseInt(strTerms);
                        ((CertificateOfDeposit) account).setMaturity(terms);
                    }
                }

                if (!validateAccountNumber(account)){
                    JOptionPane.showMessageDialog(null, "Account number already in use. Please enter a different account number.");
                    return;
                }

                allAccounts.add(account);
                lstAccounts.updateUI();
                accountQueue.offer(account);
            }
        });

        /*
          Import accounts from a user specified file.
          Defaults to accounts.json, uses txtImportFileName for input.
          */
        btnImportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = txtImportFileName.getText();
                try {
                    Reader reader = Files.newBufferedReader(Paths.get(fileName));
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Account.class, new AccountSerializer());
                    Gson gson = gsonBuilder.create();
                    Vector<Account> inAccounts = gson.fromJson(reader, new TypeToken<Vector<Account>>() {
                    }.getType());
                    allAccounts.addAll(inAccounts);
                    lstAccounts.updateUI();
                    reader.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnCompute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strPeriods = txtPeriods.getText();
                int periods = Integer.parseInt(strPeriods);
                allAccounts.stream().forEach(account -> {
                    account.setPeriods(periods);
                    account.compute();
                });
                lstAccounts.updateUI();
            }
        });
        cmbAccountType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbAccountType.getSelectedItem().toString().equals(Banker.CERT_OF_DEPOSIT)) {
                    txtTerms.setEnabled(true);
                    lblTerms.setEnabled(true);
                } else {
                    txtTerms.setText("");
                    txtTerms.setEnabled(false);
                    lblTerms.setEnabled(false);
                }
            }
        });

        btnInterestReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "All accounts have generated a total of "
                        + sumGeneratedInterest() + " units of currency.");
            }
        });

        btnClearInterest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearGeneratedInterest();
                JOptionPane.showMessageDialog(null, "Generated interest cleared.");
            }
        });
        btnWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout());
                Vector<Integer> accountNumbers = new Vector<Integer>();
                Account accountHolder;
                allAccounts.forEach(account1 -> accountNumbers.add(account1.getAccountNumber()));
                JLabel lblLowestAccountlbl = new JLabel("Lowest Account: ");
                JLabel lblLowestAccount = new JLabel();
                accountHolder = FetchNextLowestAccount();
                lblLowestAccount.setText(""+accountHolder.getAccountNumber());
                JLabel lblWithdrawAmountlbl = new JLabel("Withdraw Amount: $");
                JTextField withdrawAmount = new JTextField();
                withdrawAmount.setPreferredSize( new Dimension( 200, 24 ) );

                panel.add(lblLowestAccountlbl);
                panel.add(lblLowestAccount);
                panel.add(lblWithdrawAmountlbl);
                panel.add(withdrawAmount);
                int input = JOptionPane.showOptionDialog(null, panel, "Withdraw",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION)
                {
                    double dbleWithdrawAmount = Double.parseDouble(withdrawAmount.getText());
                    withdrawByLowestRate(dbleWithdrawAmount, accountHolder);
                }

            }
        });
    }

    /**
     * calculcates the ammount of money the user wants to withdraw from an account if the account
     * they selected and the account number (while cycling through the accounts) match
     * @param wAmmount
     * @param accHolder
     */
    public void withdrawByLowestRate(double wAmmount, Account accHolder)
    {
        int accountSelected = accHolder.getAccountNumber();
        for (Account account: allAccounts)
        {
            boolean doesEqual = false;
            if (account.getAccountNumber() == accountSelected)
            {
                account.setBalance(account.getBalance() - wAmmount);
                doesEqual = true;
            }
        }
    }

    /**
     * Initialize the account type combo box with the different account types
     */
    private void initializeAccountTypeComboBox() {
        DefaultComboBoxModel<String> accountTypesModel = new DefaultComboBoxModel<>();
        accountTypesModel.addElement(Banker.SAVINGS);
        accountTypesModel.addElement(Banker.CHECKING);
        accountTypesModel.addElement(Banker.CERT_OF_DEPOSIT);
        cmbAccountType.setModel(accountTypesModel);
    }

    /**
     * Check all JTextField components in pnlInnerNorth to see if they are empty
     * @return Returns true if valid inputs, false if any empty strings
     */
    private boolean validateInputs() {
        Component[] components = pnlInnerNorth.getComponents();
        for (Component component : components) {
            if (component.getClass().equals(JTextField.class)){
                if (component.isEnabled() &&  ((JTextField) component).getText().equals("")){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks to see if an account number is already in use
     * @param account Account object being checked for duplicate (by account number)
     * @return true if the account number is unique
     */
    public boolean validateAccountNumber(Account account){
        int accountNumber = account.getAccountNumber();
        Vector<Integer> accountNumbers = new Vector<Integer>();
        allAccounts.forEach(account1 -> accountNumbers.add(account1.getAccountNumber()));

        return !accountNumbers.contains(accountNumber);
    }

    /**
     * Sums the total amount of generated interest from all accounts
     * @return total amount of generated interest from all accounts
     */
    public double sumGeneratedInterest(){
        double totalInterest = 0;
        for (Account account:allAccounts) {
            totalInterest += account.getGeneratedInterest();
        }
        return totalInterest;
    }

    /**
     * Clears the amount of generated interest from each account
     */
    private void clearGeneratedInterest(){
        allAccounts.forEach(account -> account.setGeneratedInterest(0));
    }

    /**
     * fetches the account from the Priority Queue with the lowest interest rate
     * @return the next account with the lowest interest rate. NOTE: does not remove it from the queue
     */
    public static Account FetchNextLowestAccount()
    {
        return accountQueue.peek();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankerForm");
        frame.setContentPane(new BankerForm().pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
