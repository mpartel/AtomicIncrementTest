package fi.helsinki.cs.atomicincrementtest;

import java.util.concurrent.atomic.AtomicLongArray;

public class Main {
    private static final int numThreads = 10;
    private static final int numIncrements = 1000000;
    private static final int warmupCycles = 5;
    private static final int benchmarkCycles = 5;
    
    public static void main(String[] args) {
        MyAtomicLongArray myArray = new MyAtomicLongArray(1);
        Runnable myInc = new MyIncrementer(myArray, 0, numIncrements);
        Benchmark myBenchmark = makeBenchmark(myInc);
        
        AtomicLongArray defArray = new AtomicLongArray(1);
        Runnable defInc = new DefaultIncrementer(defArray, 0, numIncrements);
        Benchmark defBenchmark = makeBenchmark(defInc);
        
        long[] unsafeArray = new long[1];
        Runnable unsafeInc = new UnsafeIncrementer(unsafeArray, 0, numIncrements);
        Benchmark unsafeBenchmark = makeBenchmark(unsafeInc);

        ThreadLocalIncrementer threadLocalInc = new ThreadLocalIncrementer(0, numIncrements);
        Benchmark threadLocalBenchmark = makeBenchmark(threadLocalInc);
        
        System.out.println("Working...");
        myBenchmark.run();
        long myResult = myBenchmark.getResult();
        System.out.println("MyAtomicLongArray: " + myResult);
        
        System.out.println("Working...");
        defBenchmark.run();
        long defResult = defBenchmark.getResult();
        System.out.println("AtomicLongArray: " + defResult);
        
        System.out.println("Working...");
        unsafeBenchmark.run();
        long unsafeResult = unsafeBenchmark.getResult();
        System.out.println("Non-atomic long[]: " + unsafeResult);

        System.out.println("Working...");
        threadLocalBenchmark.run();
        long threadLocalResult = threadLocalBenchmark.getResult();
        System.out.println("Thread-local long[]: " + threadLocalResult);
        
        System.out.println();
        System.out.printf("MyAtomicLongArray took %.2f%% of the time that AtomicLongArray took.\n", percentage(myResult, defResult));
        System.out.printf("MyAtomicLongArray took %.2f%% of the time that non-atomic long[] array took.\n", percentage(myResult, unsafeResult));
        System.out.printf("AtomicLongArray took %.2f%% of the time that non-atomic long[] array took.\n", percentage(defResult, unsafeResult));

        System.out.printf("Thread-local long[] array took %.2f%% of the time that MyAtomicLongArray took.\n", percentage(threadLocalResult, myResult));
        System.out.printf("Thread-local long[] array took %.2f%% of the time that AtomicLongArray took.\n", percentage(threadLocalResult, defResult));
        System.out.printf("Thread-local long[] array took %.2f%% of the time that non-atomic long[] took.\n", percentage(threadLocalResult, unsafeResult));
    }
    
    private static Benchmark makeBenchmark(Runnable task) {
        Benchmark singleBenchmark = new MultithreadingBenchmark(numThreads, task);
        return new RepeatedBenchmark(singleBenchmark, warmupCycles, benchmarkCycles);
    }
    
    private static double percentage(double a, double b) {
        return 100 * a / b;
    }
}
