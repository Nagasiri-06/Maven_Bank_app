package com.example.Maven_Banck_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankApplicationTest {

    private BankApplication account;

    @BeforeEach
    public void setUp() {
        account = new BankApplication("ACC101", "Aarav Sharma", 1000.00);
    }

    @Test
    @DisplayName("Successful deposit increases account balance")
    public void testSuccessfulDeposit() {
        account.deposit(500.00);
        assertEquals(1500.00, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Successful withdrawal decreases account balance")
    public void testSuccessfulWithdrawal() {
        account.withdraw(400.00);
        assertEquals(600.00, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Withdrawal exceeding balance throws IllegalStateException")
    public void testInsufficientBalanceWithdrawal() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> account.withdraw(1500.00)
        );
        assertEquals("Insufficient funds for withdrawal.", exception.getMessage());
        assertEquals(1000.00, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Zero or negative deposit and withdrawal amounts throw IllegalArgumentException")
    public void testZeroOrNegativeTransactionAmounts() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> account.deposit(0.00)),
                () -> assertThrows(IllegalArgumentException.class, () -> account.deposit(-200.00)),
                () -> assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.00)),
                () -> assertThrows(IllegalArgumentException.class, () -> account.withdraw(-100.00))
        );
        assertEquals(1000.00, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("Accurate balance after multiple sequential deposits and withdrawals")
    public void testBalanceAfterMultipleTransactions() {
        account.deposit(2500.00);   // 3500.00
        account.withdraw(1200.00);  // 2300.00
        account.deposit(300.75);    // 2600.75
        account.withdraw(600.75);   // 2000.00

        assertEquals(2000.00, account.getBalance(), 0.001);
    }
}