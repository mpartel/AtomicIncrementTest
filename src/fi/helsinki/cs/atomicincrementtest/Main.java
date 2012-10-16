package fi.helsinki.cs.atomicincrementtest;

import java.util.concurrent.atomic.AtomicLongArray;

public class Main {
    public static void main(String[] args) {
        int numThreads = 10;
        int numIncrements = 1000000;
        int warmupCycles = 3;
        int benchmarkCycles = 5;
        
        MyAtomicLongArray myArray = new MyAtomicLongArray(1);
        Runnable myInc = new MyIncrementer(myArray, 0, numIncrements);
        Benchmark mySingleBenchmark = new MultithreadingBenchmark(numThreads, myInc);
        Benchmark myBenchmark = new MultiRunBenchmark(mySingleBenchmark, warmupCycles, benchmarkCycles);
        
        AtomicLongArray defArray = new AtomicLongArray(1);
        Runnable defInc = new DefaultIncrementer(defArray, 0, numIncrements);
        Benchmark defSingleBenchmark = new MultithreadingBenchmark(numThreads, defInc);
        Benchmark defBenchmark = new MultiRunBenchmark(defSingleBenchmark, warmupCycles, benchmarkCycles);
        
        System.out.println("Working...");
        myBenchmark.run();
        long myResult = myBenchmark.getResult();
        System.out.println("My array: " + myResult);
        
        System.out.println("Working...");
        defBenchmark.run();
        long defResult = defBenchmark.getResult();
        System.out.println("Default array: " + defResult);
        
        double percentage = 100 * ((double)myResult) / ((double)defResult);
        System.out.printf("My array took %.2f%% of the time that default array took.\n", percentage);
    }
}
