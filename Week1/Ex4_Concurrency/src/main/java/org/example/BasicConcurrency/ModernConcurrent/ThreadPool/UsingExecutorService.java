package org.example.BasicConcurrency.ModernConcurrent.ThreadPool;

import org.example.BasicConcurrency.Entity.ResultCallable;
import org.example.BasicConcurrency.Entity.task;
import org.example.BasicConcurrency.Entity.taskCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class UsingExecutorService {
    static int corePoolSize = 10;
    static int maxPoolSize = 20;
    static long keepAliveTime = 3000;
    static TimeUnit unit;
    static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(128);
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * @param corePoolSize The minimum number of threads to keep alive <br>
         *        -> These threads are always kept ready to handle tasks.
         * @param maximumPoolSize The maximum number of threads allowed in the pool.
         * @param keepAliveTime The time idle threads beyond the core size will wait
         *                      before being terminated
         * @param unit the time unit for the {@code keepAliveTime} argument
         * @param workQueue The queue holding tasks waiting to be executed.
         *
         * @Behavior:
         * Up to 10 core threads are always kept.
         * If 10 tasks are running, new tasks go into the queue (up to 128 tasks waiting).
         * If the queue is full, we have fewer than 20 threads, new threads are created (up to 20 total).
         * If 20 threads are busy and the queue is full → new tasks are rejected using the AbortPolicy (throws RejectedExecutionException).
         */

        /*
            PART 1
         */
        // Still create manually thread pool executor, reuse thread efficiently
        // -> Full control
        ExecutorService executorServices = Executors.newFixedThreadPool(5);
        ExecutorService executorService =
                new ThreadPoolExecutor(corePoolSize,maxPoolSize, keepAliveTime, unit.MILLISECONDS,taskQueue);
//        for (int i = 0;i < 100;i++) {
//            String msg = "Task "+ i;
//            executorService.execute(new task(msg));
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        // =========================================

//        List<Future<ResultCallable>> futures = new ArrayList<>();
//        for (int i = 0;i < 10;i++) {
//            String msg = "Task "+ i;
//            futures.add(executorService.submit(new taskCallable(0,msg)));
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        System.out.println("Done?");
//
//        for (Future<?> f : futures) {
//            try {
//                System.out.println(f.get());    // block when you explicitly call get()
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


        // ==========================================


        List<taskCallable> tasks = new ArrayList<>();
        for (int i=0; i < 100; i++) {
            String msg = "Task "+ i;
            tasks.add(new taskCallable(0,msg));
        }
//        // Block until got first done
//        ResultCallable resultCallable = executorService.invokeAny(tasks);
//        System.out.println(resultCallable);

        // ====================================

        List<Future<ResultCallable>> futures = executorService.invokeAll(tasks);
        for (Future<?> f : futures) {
            try {
//                // Cancel task
//                f.cancel(true);
                System.out.println(f.get());    // block when you explicitly call get()
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // ===========================================

        executorService.shutdown();
    }
}
