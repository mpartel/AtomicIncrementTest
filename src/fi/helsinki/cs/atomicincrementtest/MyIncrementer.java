
package fi.helsinki.cs.atomicincrementtest;

public class MyIncrementer implements Runnable {
    private final MyAtomicLongArray array;
    private final int offset;
    private final int increments;

    public MyIncrementer(MyAtomicLongArray array, int offset, int increments) {
        this.array = array;
        this.offset = offset;
        this.increments = increments;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < increments; ++i) {
            array.inc(offset);
        }
    }
}
