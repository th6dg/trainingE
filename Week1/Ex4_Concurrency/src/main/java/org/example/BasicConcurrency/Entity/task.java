package org.example.BasicConcurrency.Entity;

public class task implements Runnable{
    private String nameTask;

    public task(String nameTask) {
        this.nameTask = nameTask;
    }
    @Override
    public void run() {
        System.out.println("Thread "+Thread.currentThread().getName() + " run "+this.nameTask);
    }
}
