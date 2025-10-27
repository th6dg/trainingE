package org.example.BasicConcurrency.Entity;

import java.util.concurrent.Callable;

/**
 * <dl>
 * <dt> {@code Limitation} of {@code Runnable} interface: </dt>
 *      <dd> It cannot return a result </dd>
 *      <dd> It cannot throw checked exceptions</dd>
 *
 * <dt>{@code Callable}: functional interface, return a result (V) and throw checked exceptions</dt>
 *      <dd>submitted to an {@code ExecutorService} - runs asynchronously</dd>
 *      <dd>returns a {@code Future} representing the result.</dd>
 *
 * <dt>{@code Feature}: holds the result that will be available once the task finishes.</dt>
 *      <dd>{@code get()}: retrieves the result (waits if necessary).</dd>
 *      <dd>{@code isDone()}: checks if the task is completed.</dd>
 *      <dd>{@code cancel()}: attempts(try) to cancel the task.</dd>
 *      <dd>{@code isCancelled()}: checks if it was cancelled.</dd>
 * <dt>{@code ExecutorService} </dt>
 *      <dd>{@code submit()}: no-block, task -> future, handle later</dd>
 *      <dd>{@code invokeAll()}: block, List<(task)> -> reseult, get all results</dd>
 *      <dd>{@code invokeAny()}: block, List<(task)> -> List<(future)>, get first results</dd>
 * </dl>
 *
 */
public class taskCallable implements Callable<ResultCallable> {
    Integer number;
    String nameTask;
    public taskCallable(Integer number, String nameTask){
        this.number = number;
        this.nameTask = nameTask;
    }
    @Override
    public ResultCallable call() throws Exception {
        System.out.println("Thread "+Thread.currentThread().getName() + " run "+this.nameTask);
        int sum = 0;
        Thread.sleep(100);
        for (int i =0;i<10000;i++) {
            sum = sum + this.number;
            number = number+1;
        }
        return new ResultCallable(sum, nameTask);
    }
}
