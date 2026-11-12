package com.example.Maven_Banck_app;

import java.util.Scanner;

public class BankApplication {
    private final String accountNumber;
    private final String accountHolderName;
    private double balance;

    public BankApplication(String accountNumber, String accountHolderName, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds for withdrawal.");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public void displayBalance() {
        System.out.printf("Account [%s - %s] Current Balance: ₹%.2f%n",
                accountNumber, accountHolderName, balance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Simple Banking Application ===");
        System.out.print("Enter Account Number: ");
        String accNumber = scanner.nextLine();

        System.out.print("Enter Account Holder Name: ");
        String holderName = scanner.nextLine();

        System.out.print("Enter Initial Balance (₹): ");
        double initialBalance = scanner.nextDouble();

        BankApplication account;
        try {
            account = new BankApplication(accNumber, holderName, initialBalance);
            System.out.println("Account created successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            scanner.close();
            return;
        }

        int choice;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Display Balance");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter deposit amount (₹): ");
                        double depositAmt = scanner.nextDouble();
                        account.deposit(depositAmt);
                        System.out.println("Deposit successful.");
                    }
                    case 2 -> {
                        System.out.print("Enter withdrawal amount (₹): ");
                        double withdrawAmt = scanner.nextDouble();
                        account.withdraw(withdrawAmt);
                        System.out.println("Withdrawal successful.");
                    }
                    case 3 -> account.displayBalance();
                    case 4 -> System.out.println("Exiting application. Thank you!");
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Transaction failed: " + e.getMessage());
            }
        } while (choice != 4);

        scanner.close();
    }
}