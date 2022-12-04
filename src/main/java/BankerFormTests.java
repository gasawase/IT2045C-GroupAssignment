import com.bank.*;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class BankerFormTests {

    @Test
    public void withdrawLowestRate() {
        BankerForm bankerForm = new BankerForm();
        Account emptyAccount;
        double testWithdraw = 100.0;

        Object CHECKING = new Object();
        Object SAVINGS = new Object();
        Account account1 = Banker.createAccount(CHECKING);
        account1.setBalance(72532);
        account1.setInterest(56);
        account1.setPeriods(12);
        Account account2 = Banker.createAccount(SAVINGS);
        account2.setBalance(234);
        account2.setInterest(23);
        account2.setPeriods(12);

        bankerForm.allAccounts.add(account1);
        bankerForm.allAccounts.add(account2);
        bankerForm.accountQueue.offer(account1);
        bankerForm.accountQueue.offer(account2);

        emptyAccount = bankerForm.FetchNextLowestAccount();

        bankerForm.withdrawByLowestRate(testWithdraw, emptyAccount);
        assertEquals(134.0, emptyAccount.getBalance(), 1.0);
    }

    @Test
    public void checkDuplicateAccounts()
    {
        BankerForm bankerForm = new BankerForm();
        Object CHECKING = new Object();

        Account account1 = Banker.createAccount(CHECKING);
        account1.setAccountNumber(1);
        Account account2 = Banker.createAccount(CHECKING);
        account2.setAccountNumber(1);
        bankerForm.allAccounts.add(account1);

        assertEquals(false, bankerForm.validateAccountNumber(account2));

    }

    @Test
    public void checkForTotalInterestEarned()
    {
        BankerForm bankerForm = new BankerForm();
        Object SAVINGS = new Object();
        Account account1 = Banker.createAccount(SAVINGS);
        Account account2 = Banker.createAccount(SAVINGS);

        account1.setInterest(15.0);
        account1.setPeriods(2);
        account1.setBalance(100.0);
        account2.setInterest(20.0);
        account2.setPeriods(2);
        account2.setBalance(100.0);

        bankerForm.allAccounts.add(account1);
        bankerForm.allAccounts.add(account2);

        bankerForm.allAccounts.stream().forEach(account -> {
            account.compute();
        });

        assertEquals(76.25, bankerForm.sumGeneratedInterest(), 1.0);
    }
}
