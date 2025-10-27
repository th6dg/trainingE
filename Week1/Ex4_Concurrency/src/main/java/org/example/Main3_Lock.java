package org.example;


import org.example.BasicConcurrency.Entity.BankAccount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main3_Lock {
    // Use lock instead of synchonize
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BankAccount bankAccount = new BankAccount(0L);

        for (long i = 0; i < 10; i++) {
            Long moneyTransfer = (long) i;
            executorService.submit(() -> {
                bankAccount.addMoneyWithLock(moneyTransfer, lock);
            });
        }
        executorService.shutdown();
        if (executorService.awaitTermination(100, TimeUnit.SECONDS)) {
            System.out.println("Task Done");
            System.out.println("Final account " + bankAccount.getValue());
        }
        else {
            System.out.println("Timeout! Some tasks are still running.");
        }


    }
}
