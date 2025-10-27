package org.example.BasicConcurrency;

import org.example.BasicConcurrency.Entity.Number;

/**
 * @Theory:
 * Thread is flow of execution.<br>
 * JVM allow to create new thread from exist thread.<br>
 * When code running in some thread creates a new Thread object, <br>
 * the new thread come to play.
 * @Basic:
 *  Thread Class, Runnable interface <br>
 *  Synchonize<br>
 */
public class BasicConcurrency implements Runnable{

    private org.example.BasicConcurrency.Entity.Number number;  // Integer is immutable, so use custom class
    public BasicConcurrency(org.example.BasicConcurrency.Entity.Number number) {
        this.number = number;

    }
    @Override
    public void run() {
        System.out.println(number);

        for (int i = 0; i < 100; i++) {
            // ++ operation can be very fast, so make it slower
            number.increase();
            try {
                Thread.sleep(0,1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Thread running is: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        org.example.BasicConcurrency.Entity.Number number1 = new Number(0);
        BasicConcurrency basicConcurrency = new BasicConcurrency(number1);
        // Pass runnable implement into thread class
        Thread thread1 = new Thread(basicConcurrency);
        Thread thread2 = new Thread(basicConcurrency);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("Final count: "+number1.getValue());
    }
}
