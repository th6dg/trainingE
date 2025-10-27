package org.example.BasicConcurrency.Entity;

import java.util.LinkedList;
import java.util.List;

// Thread communication
public class Consumer {
    public void consume(LinkedList<Number> numbers) throws InterruptedException {
        while (true) {
            synchronized (numbers) {
                while (numbers.isEmpty()) {
                    numbers.wait();
                }
                Number val = numbers.pop();
                System.out.println("Consumed: " + val.getValue());
                numbers.notifyAll();
                Thread.sleep(1000);
            }
        }
    }
}
