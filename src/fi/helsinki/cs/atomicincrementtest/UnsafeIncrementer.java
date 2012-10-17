package fi.helsinki.cs.atomicincrementtest;

public class UnsafeIncrementer implements Runnable {
    private final long[] array;
    private final int offset;
    private final int increments;

    public UnsafeIncrementer(long[] array, int offset, int increments) {
        this.array = array;
        this.offset = offset;
        this.increments = increments;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < increments; ++i) {
            ++array[offset];
        }
    }
}
