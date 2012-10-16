
package fi.helsinki.cs.atomicincrementtest;

import java.util.concurrent.atomic.AtomicLongArray;

public class DefaultIncrementer implements Runnable {
    private final AtomicLongArray array;
    private final int offset;
    private final int increments;

    public DefaultIncrementer(AtomicLongArray array, int offset, int increments) {
        this.array = array;
        this.offset = offset;
        this.increments = increments;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < increments; ++i) {
            // getAndIncrement vs incrementAndGet doesn't seem to matter.
            // Both are implemented very similarly.
            array.incrementAndGet(offset);
        }
    }
}
