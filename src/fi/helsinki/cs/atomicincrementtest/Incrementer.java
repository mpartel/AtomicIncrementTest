package fi.helsinki.cs.atomicincrementtest;

public interface Incrementer<T> extends Runnable {
    public void setIncrementee(T obj);
}
