package org.example.BasicConcurrency.Entity;

public class C {
    public State state;
    public C(State state) {
        this.state = state;
    }
    public void printCharC() throws InterruptedException {
        while (true) {
            synchronized (state) {
                if (state.value == 2) {
                    System.out.println("C");
                    // Make sure always got thread in entry list
                    state.notifyAll();
                    state.value = 0;
                    Thread.sleep(1000);
                } else {
                    state.wait();
                }
            }
        }
    }
}
