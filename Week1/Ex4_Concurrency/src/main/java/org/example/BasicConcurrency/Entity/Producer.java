package org.example.BasicConcurrency.Entity;

import java.util.LinkedList;
import java.util.List;

// Thread communication
public class Producer {
    private int maxSize =1;
    private int value = 0;
    public void produce(LinkedList<Number> numbers) throws InterruptedException {
        while (true) {
            synchronized (numbers) {
                while (numbers.size()==maxSize) {
                    // Thread nhả khóa (lock)
                    // JVM đưa thread đó vào Wait Set
                    // Thread tạm dừng, không chạy tiếp dòng sau wait().
                    numbers.wait();
                }
                value++;
                numbers.add(new Number(value));
                System.out.println("Produced: " + value);
                // JVM đánh thức tất cả thread đang chờ trong Wait Set
                // Những thread này được chuyển sang Entry List
                numbers.notifyAll();
                Thread.sleep(1000);
            }
        }
    }


}
