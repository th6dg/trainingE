package org.example.BasicConcurrency.Entity;

public class ResultCallable {
    Integer sum;
    String taskName;

    public ResultCallable(Integer sum, String taskName){
        this.sum = sum;
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "        Result: sum=" + sum + ", taskName='" + taskName;
    }
}
