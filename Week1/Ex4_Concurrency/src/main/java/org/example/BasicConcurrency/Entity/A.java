package org.example.BasicConcurrency.Entity;

public class A {
    public State state;
    public A(State state) {
        this.state = state;
    }
    public void printCharA() throws InterruptedException {
        while (true) {
            synchronized (state) {
                if (state.value == 0) {
                    System.out.println("A");
                    // Make sure always got thread in entry list
                    state.notifyAll();
                    state.value = 1;
                    Thread.sleep(1000);
                } else {
                    state.wait();
                }
            }
        }
    }
}
