package org.example.BasicConcurrency.ModernConcurrent.ThreadPool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Why: {@code CompletableFuture}?<br>
 * {@code asynchronous programming}<br>
 * Limitation of {@code Future}:<br>
 * + Can not be complete manually<br>
 * + Action can not be performed until result available<br>
 * + Can not attach callback function(notification)
 * + Can not chained<br>
 * + Can not combined<br>
 * + No exception handling<br>
 * @Concept:
 * Asynchronous task execution<br>
 * Non-blocking chaining<br>
 * Future + Completion stages<br>
 * @UseCase:
 * Combining multiple API calls<br>
 * Running background computations<br>
 * Non-blocking pipelines<br>
 * @Pattern:
 * {@code apply}: Takes input, returns output       (Function)<br>
 * {@code accept}: Takes input, returns nothing     (Consumer)<br>
 * {@code run}: Takes no input, returns nothing     (Runnable)<br>
 * {@code supply}: Takes no input, returns output   (Supplier)<br>
 * {@code then}<br>
 * {@code either}<br>
 * {@code both}<br>
 * {@code combine}<br>
 * {@code get}<br>
 * {@code join}<br>
 *
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        // 1. Khởi tạo completable future
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        // 2. Execute code asynchronously
        // runAsync:  No argument, no return
        CompletableFuture<Void> cfr = CompletableFuture
                .runAsync(() -> {
            try {
                Thread.sleep(10000);
                System.out.println("Done Hello world");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        })
                .thenRun(() -> System.out.println("Done run async"));

        // supplyAsync
        CompletableFuture<Integer> cfs= CompletableFuture
                .supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 42;
        })
                // callback but (chaining of asynchronous computation)
                // execute in same thread
                .thenApply(result -> result + 10);
        System.out.println(cfs);
        System.out.println("Hi");

        // then Compose, flatten
        cfs.thenCompose(results ->
                CompletableFuture.supplyAsync(() -> {
                    if (false) {
                        throw new RuntimeException("Error");
                    }
                    System.out.println("After use thenCompose " + results);
                    return results * 2;
                })
        );
        CompletableFuture<Integer> cfs2 = CompletableFuture.supplyAsync(() -> {return 100;})
                .thenCombine(cfs, (a,b) -> a+b);

        cfr.get();
        System.out.println("CFS2 "+cfs2.get());

        // All of

        // Any of

    }
}
