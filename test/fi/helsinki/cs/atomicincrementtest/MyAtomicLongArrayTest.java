package fi.helsinki.cs.atomicincrementtest;

import static org.junit.Assert.*;
import org.junit.Test;

public class MyAtomicLongArrayTest {
    @Test
    public void testWithManyThreads() throws InterruptedException {
        int numThreads = 4;
        int numIncrements = 1000000;
        int arrayIndex = 57;
        
        MyAtomicLongArray array = new MyAtomicLongArray(arrayIndex + 1);
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; ++i) {
            MyIncrementer incrementer = new MyIncrementer(array, arrayIndex, numIncrements);
            threads[i] = new Thread(incrementer);
            threads[i].setDaemon(true);
            threads[i].start();
        }
        
        for (int i = 0; i < numThreads; ++i) {
            threads[i].join();
        }
        
        assertEquals(numThreads * numIncrements, array.get(arrayIndex));
    }
}
