package fi.helsinki.cs.atomicincrementtest;

public interface Benchmark extends Runnable {
    public long getResult();
}
