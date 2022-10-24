import com.bank.Account;
import com.bank.Banker;
import com.bank.CertificateOfDeposit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private Vector<Account> allAccounts = new Vector<Account>();

    public BankerForm() {

        initializeAccountTypeComboBox();

        lstAccounts.setListData(allAccounts);

        btnAddAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                        //CertificateOfDeposit certificateOfDeposit = (CertificateOfDeposit) account;
                        String strTerms = txtTerms.getText();
                        int terms = Integer.parseInt(strTerms);
                        ((CertificateOfDeposit) account).setMaturity(terms);
                        //certificateOfDeposit.setMaturity(terms);
                    }
                }


                allAccounts.add(account);
                lstAccounts.updateUI();
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

    private void initializeAccountTypeComboBox() {
        DefaultComboBoxModel<String> accountTypesModel = new DefaultComboBoxModel<>();
        accountTypesModel.addElement(Banker.SAVINGS);
        accountTypesModel.addElement(Banker.CHECKING);
        accountTypesModel.addElement(Banker.CERT_OF_DEPOSIT);
        cmbAccountType.setModel(accountTypesModel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankerForm");
        frame.setContentPane(new BankerForm().pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
