package org.example.BasicConcurrency.Entity;

public class Number {
    public int value;
    public Number (int value) {
        this.value = value;
    }
    public void increase() {
        for (int i = 0; i < 100; i++) {
            this.value++;
        }
    }
    public int getValue() {
        return this.value;
    }
}
