package bank;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Test cases for the BankAccount class.
 */
public class BankAccountTest {

    private BankAccount account;

    @Before
    public void setUp() {
        account = new BankAccount("ACC001", 1000.0);
    }

    @Test
    public void testConstructor() {
        BankAccount newAccount = new BankAccount("ACC002", 500.0);
        assertEquals(500.0, newAccount.getBalance(), 0.01);
        assertEquals("ACC002", newAccount.getAccountNumber());
        assertFalse(newAccount.isFrozen());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeBalance() {
        new BankAccount("ACC003", -100.0);
    }

    @Test
    public void testDeposit() {
        assertTrue(account.deposit(500.0));
        assertEquals(1500.0, account.getBalance(), 0.01);
    }

    @Test
    public void testDeposit2() {
        assertFalse(account.deposit(0.0));
        assertEquals(1000.0, account.getBalance(), 0.01);
    }

    @Test
    public void testDeposit3() {
        assertFalse(account.deposit(-100.0));
        assertEquals(1000.0, account.getBalance(), 0.01);
    }

    @Test
    public void testWithdraw() {
        assertTrue(account.withdraw(300.0));
        assertEquals(700.0, account.getBalance(), 0.01);
    }

    @Test
    public void testWithdraw2() {
        assertFalse(account.withdraw(1500.0));
        assertEquals(1000.0, account.getBalance(), 0.01);
    }

    @Test
    public void testWithdraw3() {
        assertTrue(account.withdraw(1000.0));
        assertEquals(0.0, account.getBalance(), 0.01);
    }

    @Test
    public void testWithdraw4() {
        assertFalse(account.withdraw(-100.0));
        assertEquals(1000.0, account.getBalance(), 0.01);
    }

    @Test
    public void testTransfer() {
        BankAccount recipient = new BankAccount("ACC002", 500.0);
        assertTrue(account.transfer(300.0, recipient));
        assertEquals(700.0, account.getBalance(), 0.01);
        assertEquals(800.0, recipient.getBalance(), 0.01);
    }

    @Test
    public void testTransfer2() {
        BankAccount recipient = new BankAccount("ACC002", 500.0);
        assertFalse(account.transfer(1500.0, recipient));
        assertEquals(1000.0, account.getBalance(), 0.01);
        assertEquals(500.0, recipient.getBalance(), 0.01);
    }

    @Test
    public void testTransfer3() {
        BankAccount recipient = new BankAccount("ACC002", 500.0);
        recipient.freeze();
        assertFalse(account.transfer(300.0, recipient));
        assertEquals(1000.0, account.getBalance(), 0.01);
        assertEquals(500.0, recipient.getBalance(), 0.01);
    }

    @Test
    public void testFreezeAccount() {
        account.freeze();
        assertTrue(account.isFrozen());
        assertFalse(account.deposit(100.0));
        assertFalse(account.withdraw(100.0));
    }

    @Test
    public void testUnfreezeAccount() {
        account.freeze();
        account.unfreeze();
        assertFalse(account.isFrozen());
        assertTrue(account.deposit(100.0));
    }

    @Test
    public void testApplyMonthlyInterest() {
        account.applyMonthlyInterest(0.12); // 12% annual = 1% monthly
        assertEquals(1010.0, account.getBalance(), 0.01);
    }

    @Test
    public void testApplyMonthlyInterest2() {
        account.applyMonthlyInterest(0.12); // 12% annual
        account.applyMonthlyInterest(0.12);
        assertEquals(1020.10, account.getBalance(), 0.01);
    }

    @Test
    public void testApply3() {
        double initialBalance = account.getBalance();
        account.applyMonthlyInterest(-0.05);
        assertEquals(initialBalance, account.getBalance(), 0.01);
    }
}
