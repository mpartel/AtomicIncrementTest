package fi.helsinki.cs.atomicincrementtest;

public class ThreadLocalIncrementer implements Runnable {
    private volatile int initialSize = 1;

    private final ThreadLocal<long[]> tlArray = new ThreadLocal<long[]>() {
        @Override
        protected long[] initialValue() {
            return new long[initialSize];
        }
    };

    private final int offset;
    private final int increments;

    public ThreadLocalIncrementer(int offset, int increments) {
        this.offset = offset;
        this.increments = increments;
    }

    @Override
    public void run() {
        for (int i = 0; i < increments; ++i) {
            ++tlArray.get()[offset];
        }
    }
}
