package org.example.BasicConcurrency.Entity;

import java.util.Random;

public class BankAccountService2{
    public BankAccount bankAccount;
    Random random = new Random();

    public BankAccountService2(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public synchronized void addMoney() {
        Long moneyTransfer= Math.abs(random.nextLong());;
        while (bankAccount.getValue() > 1000000000) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        bankAccount.addMoney(moneyTransfer);
        notifyAll();
    }

    public synchronized void minusMoney() {
        Long moneyTransfer= Math.abs(random.nextLong());
        while (moneyTransfer > bankAccount.getValue()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        bankAccount.minusMoney(moneyTransfer);
        notifyAll();
    }
}
