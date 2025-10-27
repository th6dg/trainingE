package org.example.BasicConcurrency.ModernConcurrent.ThreadPool;

import org.example.BasicConcurrency.Entity.ThreadPool;


/**
 * @Problem:
 * 100000 task?<br>
 *  Chi phí của việc tạo 1 thread là tương đối lớn, <br>
 *  thường dẫn tới các vấn đề về hiệu năng và cấp phát dữ liệu.<br>
 * Tạo 1 triệu thread có thể chậm, sập chương trình<br>
 * @Overview:
 *  A thread pool maintains a fixed number of threads<br>
 *  Không cần tạo thread thủ công
 * @Benefit:
 * Better Performance<br>
 * Reusability thread<br>
 * Resource Management<br>
 * @Threadpool:
 * Cached thread pool<br>
 * Fixed thread pool<br>
 * Single-threaded pool<br>
 * Fork/Join pool<br>
 * @HowToImplement:
 * Create threadpool manually:<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create N threads upfront.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Maintain a task queue.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Threads continuously take tasks from the queue and execute them.<br>
 * Using Executor Service<br>
 */
public class ThreadPoolExample {
    public static void main(String[] args) throws Exception {
        // Create thread pool manually from scratch
        // worker is waiting for task, run in another thread
        ThreadPool threadPool = new ThreadPool(3, 10);

        for(int i=0; i<100; i++) {

            int taskNo = i;
            // add task for queue
            threadPool.execute( new Runnable() {
                @Override
                public void run() {
                    String message =
                            Thread.currentThread().getName()
                                    + ": Task " + taskNo ;
                    System.out.println(message);
                }
            });
        }

        threadPool.waitUntilAllTasksFinished();
        threadPool.stop();

    }
}

