package org.example.BasicConcurrency.ThreadCommunication;

import org.example.BasicConcurrency.Entity.*;
import org.example.BasicConcurrency.Entity.Number;

import java.util.LinkedList;

/**
 * @Overview:
 * Thread lấy khóa/nhả khóa dựa trên điều kiện cụ thể
 * @Term:
 * Entry List: Thread chờ lấy lock <br>
 * Wait Set: Chờ được “đánh thức” bằng notify() hoặc notifyAll()  ->  Vào Entry List chờ lock<br>
 * Flow: <br>
 * Thread gọi wait() chưa muốn chạy tiếp, mà muốn đợi điều kiện thay đổi.<br>
 * Nếu thread vẫn ở Entry Set:<br>
 * Nó sẽ tranh giành khóa ngay lập tức → không đúng mục đích “đợi điều kiện”.<br>
 * Khi thread ở Wait Set:<br>
 * Nó tạm nghỉ, giải phóng khóa, nhường cơ hội cho thread khác thay đổi trạng thái.<br>
 * Khi điều kiện thay đổi → notify → chuyển sang Entry Set để giành lại khóa và tiếp tục.<br>
 * @UseCase:
 * Producer / Consumer
 * Có 1 thread Producer và 1 thread Consumer.
 * Producer thêm số nguyên vào List<Integer>.
 * Consumer lấy số nguyên ra khỏi list và in ra.
 * Giới hạn list tối đa 5 phần tử.
 */
public class ThreadCommunication {
    public static void main(String[] args) {
        LinkedList<Number> numbers = new LinkedList<>();
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        State state = new State(0);
        A a = new A(state);
        Thread tA = new Thread(() -> {
            try {
                a.printCharA();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        B b = new B(state);
        Thread tB = new Thread(() -> {
            try {
                b.printCharB();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        C c = new C(state);
        Thread tC = new Thread(() -> {
            try {
                c.printCharC();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread produce = new Thread(() -> {
            try {
                producer.produce(numbers);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consume = new Thread(() -> {
            try {
                consumer.consume(numbers);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

//        produce.start();
//        consume.start();
        tA.start();
        tB.start();
        tC.start();
    }

}
