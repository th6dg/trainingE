package org.example.BasicConcurrency.Synchonize;

import org.example.BasicConcurrency.Entity.Number;

/**
 * @synchonized:
 *  Lock entire method
 *  Lock only small block in method
 *  Lock static
 */
public class SynchonizeExample {
    public org.example.BasicConcurrency.Entity.Number number;

    public SynchonizeExample(org.example.BasicConcurrency.Entity.Number number) {
        this.number = number;
    }

    public synchronized void increase() {
            System.out.println("Thread "+Thread.currentThread().getName() + " is running");
            number.increase();
    }

    public void increaseV2() {
        try {
            System.out.println("Thread "+Thread.currentThread().getName() + " is wait to run function V2");
            Thread.sleep(0,1);
            synchronized (number) {
                System.out.println("Thread "+Thread.currentThread().getName() + " is running function V2");
                number.increase();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        org.example.BasicConcurrency.Entity.Number number1 = new Number(0);
        SynchonizeExample synchonizeExample = new SynchonizeExample(number1);
        Thread t1 = new Thread(() -> synchonizeExample.increase());
        Thread t2 = new Thread(() -> synchonizeExample.increaseV2());
        Thread t3 = new Thread(() -> synchonizeExample.increaseV2());
        Thread t4 = new Thread(() -> synchonizeExample.increaseV2());
        Thread.sleep(0,1);
        t2.start();
        t1.start();
        t3.start();

        t4.start();

        t3.join();
        t4.join();
        t1.join();
        t2.join();
        System.out.println(number1.getValue());
    }

}
