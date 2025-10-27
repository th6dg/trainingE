package org.example.BasicConcurrency.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// This is the manager — it owns the queue and the worker threads.
public class ThreadPool {

    // The shared object (taskQueue) is a BlockingQueue of Runnable object
    // -> thread safe
    private BlockingQueue<Runnable> taskQueue = null;
    // runnables is <runnable object> -> thread(s)
    private List<PoolThreadRunnable> runnables = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoOfTasks){
        // ArrayBlockingQueue implement BlockingQueue
        taskQueue = new ArrayBlockingQueue<Runnable>(maxNoOfTasks);

        // number of thread
        // each poolThreadRunnable is 1 thread
        for(int i=0; i<noOfThreads; i++){
            PoolThreadRunnable poolThreadRunnable =
                    new PoolThreadRunnable(taskQueue);

            runnables.add(poolThreadRunnable);
        }
        for(PoolThreadRunnable runnable : runnables){
            new Thread(runnable).start();
        }
    }

    public synchronized void  execute(Runnable task) throws Exception{
        if(this.isStopped) throw
                new IllegalStateException("ThreadPool is stopped");

        //Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions,
        // returning true upon success and false if no space is currently available.
        this.taskQueue.offer(task);
        Thread.sleep(1000);
    }

    public synchronized void stop(){
        this.isStopped = true;
        for(PoolThreadRunnable runnable : runnables){
            runnable.doStop();
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while(this.taskQueue.size() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
