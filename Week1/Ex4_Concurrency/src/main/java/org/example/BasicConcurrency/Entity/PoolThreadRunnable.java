package org.example.BasicConcurrency.Entity;

import java.util.concurrent.BlockingQueue;

// This class represents one runnable object (thread object) in the pool.
// each thread can access to shared-taskQueue
public class PoolThreadRunnable implements Runnable {

    private Thread        thread    = null;
    private BlockingQueue taskQueue = null;
    private boolean       isStopped = false;

    public PoolThreadRunnable(BlockingQueue queue){
        taskQueue = queue;
    }

    public void run(){
        this.thread = Thread.currentThread();
        while(!isStopped()){
            try{
                // Retrieves and removes the head of this queue,
                // waiting if necessary
                Runnable runnable = (Runnable) taskQueue.take();
                // task’s code is actually executed.
                runnable.run();
            } catch(Exception e){
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    public synchronized void doStop(){
        isStopped = true;
        //break pool thread out of dequeue() call.
        this.thread.interrupt();
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }
}