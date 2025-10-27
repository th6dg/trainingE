package org.example;

import org.example.BasicConcurrency.Entity.BankAccount;
import org.example.BasicConcurrency.Entity.BankAccountService1;
import org.example.BasicConcurrency.Entity.BankAccountService2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main1_ThreadCommunication {
    // Sử dụng Thread Communication, wait(), notifyAll()
    // Producer-Consumer pattern
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount(1000L);
        BankAccountService2 bankAccountService2 = new BankAccountService2(bankAccount);
        Thread addMoney = new Thread(() ->{
            for (int i = 0; i < 100 ; i++){
                bankAccountService2.addMoney();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread minusMoney = new Thread(() ->{
            for (int i = 0; i < 100 ; i++){
                bankAccountService2.minusMoney();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        addMoney.start();
        minusMoney.start();

        addMoney.join();
        minusMoney.join();

        System.out.println("Final account "+bankAccount.getValue());
    }
}
