package org.example.BasicConcurrency.Entity;

public class B {
    public State state;
    public B(State state) {
        this.state = state;
    }
    public void printCharB() throws InterruptedException {
        while (true) {
            synchronized (state) {
                if (state.value == 1) {
                    System.out.println("B");
                    // Make sure always got thread in entry list
                    state.notifyAll();
                    state.value = 2;
                    Thread.sleep(1000);
                } else {
                    state.wait();
                }
            }
        }
    }
}
