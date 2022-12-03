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

    private Vector<Account> allAccounts = new Vector<Account>();

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

                String strBalance = txtStartBalance.getText();
                double balance = Double.parseDouble(strBalance);

                String strInterestRate = txtInterestRate.getText();
                double interestRate = Double.parseDouble(strInterestRate);

                String type = cmbAccountType.getSelectedItem().toString();
                Account account = Banker.createAccount(type);
                account.setBalance(balance);
                account.setInterest(interestRate);

                if (cmbAccountType.getSelectedItem().toString().equals(Banker.CERT_OF_DEPOSIT)) {
                    if (account instanceof CertificateOfDeposit) {
                        String strTerms = txtTerms.getText();
                        int terms = Integer.parseInt(strTerms);
                        ((CertificateOfDeposit) account).setMaturity(terms);
                    }
                }

                allAccounts.add(account);
                lstAccounts.updateUI();
            }
        });

        /**
         * Import accounts from a user specified file.
         * Defaults to accounts.json, uses txtImportFileName for input.
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankerForm");
        frame.setContentPane(new BankerForm().pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
