package org.example;

import org.example.BasicConcurrency.Entity.BankAccount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main2_ExecutorService {
    // Using thread pool, executor service
    // Không phải tạo thread thủ công
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BankAccount bankAccount = new BankAccount(0L);
        for (long i = 0; i < 100; i++) {
            Long moneyTransfer = (long) i;
            executorService.submit(() -> {
                bankAccount.addMoney(moneyTransfer);
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
