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
    
    @Test
    public void testLargeValues() {
        MyAtomicLongArray array = new MyAtomicLongArray(10);
        array.set(5, (0x100000000l) - 2);
        array.inc(5);
        array.inc(5);
        array.inc(5);
        array.inc(5);
        array.inc(5);
        assertEquals((0x100000000l) + 3, array.get(5));
        
        for (int i = 0; i < array.size(); ++i) {
            if (i != 5) {
                assertEquals(0, array.get(i));
            }
        }
    }
    
    @Test
    public void testNegativeValues() {
        MyAtomicLongArray array = new MyAtomicLongArray(10);
        array.set(5, -3);
        array.inc(5);
        array.inc(5);
        array.inc(5);
        array.inc(5);
        array.inc(5);
        assertEquals(2, array.get(5));
        
        for (int i = 0; i < array.size(); ++i) {
            if (i != 5) {
                assertEquals(0, array.get(i));
            }
        }
    }
}
