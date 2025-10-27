package org.example.BasicConcurrency.Entity;

public class BankAccountService1 implements Runnable{
    private BankAccount bankAccount;
    private String action;
    private Long money;
    public BankAccountService1(BankAccount bankAccount, String action, Long money) {
        this.bankAccount = bankAccount;
        this.action = action;
        this.money = money;
    }
    @Override
    public void run() {
        if (money < 0) {
            throw new RuntimeException();
        }
        if (action.equals("add")) {
            try {
                bankAccount.addMoney(this.money);
            }
            catch (Exception e) {
            }
        }

        if (action.equals("minus")) {
            try {
                bankAccount.minusMoney(this.money);
            }
            catch (Exception e) {
            }
        }
    }
}
