package org.example;

/**
 * Cách chạy bài 4:<br>
 * <dt>Tradditional concurrent: </dt>
 * <br>
 * <dd>{@code Thread}, {@code Runnable}, {@code synchronized}. {@code volatile}</dd> <br>
 * <dd>{@code wait()}, {@code notifyAll()}</dd><br>
 */

import org.example.BasicConcurrency.Entity.BankAccount;
import org.example.BasicConcurrency.Entity.BankAccountService1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Senario: <br>
 * Có 1 biến bank Account<br>
 * Hàm addMoney(bankAccount, money): Hàm nhận tiền
 * Hàm minusMoney(bankAccount, money): Hàm trừ tiền, throw Exception khi banhAccount < money
 *
 */
public class Main0_TraditionalApproach {
    // Chậm
    public static void main(String[] args) throws InterruptedException {
        String action;
        Random random = new Random();
        BankAccount bankAccount = new BankAccount(0L);
        List<Thread> threadsTransfer = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            Long moneyTransfer = (long) Math.abs(i);

            if (moneyTransfer % 5 ==0) {
                action = "minus";
            }

            else {
                action = "add";
            }

            Thread transferThread = new Thread(new BankAccountService1(bankAccount,action,moneyTransfer));
            threadsTransfer.add(transferThread);
            transferThread.start();
        }
        for (Thread t : threadsTransfer) {
            t.join();
        }
        System.out.println("Final account "+bankAccount.getValue());
    }
}