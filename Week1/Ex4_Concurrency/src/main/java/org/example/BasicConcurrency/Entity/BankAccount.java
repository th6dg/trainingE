package org.example.BasicConcurrency.Entity;

import java.util.concurrent.locks.Lock;

public class BankAccount {
    private String accountName = "Duong";
    private Long value;

    public BankAccount(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public String getAccountName() {
        return accountName;
    }

    public synchronized void addMoney(Long money) {
        this.value += money;
    }

    public void addMoneyWithLock(Long money, Lock lock) {
        try {
            lock.lock();
            this.value += money;
        }
        finally {
            lock.unlock();
        }
    }


    public synchronized void minusMoney(Long money) {
        if(this.value < money) {
            throw new ArithmeticException("invalid minus");
        }
        this.value -= money;
    }

    public void minusMoneyWithLock(Long money, Lock lock) {
        try {
            lock.lock();
            this.value -= money;
        }
        finally {
            lock.unlock();
        }
    }
}
