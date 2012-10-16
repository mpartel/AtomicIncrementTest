package fi.helsinki.cs.atomicincrementtest;

import java.io.File;

public class MyAtomicLongArray {
    static {
        System.load(new File("native/libatomicincrementtest.so").getAbsolutePath());
    }
    
    private static final long LONG_BYTES = Long.SIZE / 8;
    
    private long arrayPtr;
    private int size;
    
    public MyAtomicLongArray(int size) {
        this.arrayPtr = allocate(size);
        if (arrayPtr == 0) {
            throw new OutOfMemoryError("Could not allocate atomic long array of size " + size);
        }
        this.size = size;
    }
    
    private native long allocate(int size);

    public int size() {
        return size;
    }
    
    public final long get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return getFrom(arrayPtr + index * LONG_BYTES);
    }
    private native long getFrom(long addr);
    
    public final void inc(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        incAt(arrayPtr + index * LONG_BYTES);
    }
    private native void incAt(long index);

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        arrayPtr = 0;
        size = 0;
    }
}