package fi.helsinki.cs.atomicincrementtest;

import java.math.BigInteger;

public class MultiRunBenchmark implements Benchmark {
    private final Benchmark task;
    private final int warmupCycles;
    private final int benchmarkCycles;
    private long result;

    public MultiRunBenchmark(Benchmark task, int warmupCycles, int benchmarkCycles) {
        this.task = task;
        this.warmupCycles = warmupCycles;
        this.benchmarkCycles = benchmarkCycles;
    }

    @Override
    public void run() {
        for (int i = 0; i < warmupCycles; ++i) {
            task.run();
        }
        
        long[] results = new long[benchmarkCycles];
        for (int i = 0; i < benchmarkCycles; ++i) {
            task.run();
            results[i] = task.getResult();
        }
        
        result = average(results);
    }
    
    @Override
    public long getResult() {
        return result;
    }

    private long average(long[] results) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < results.length; ++i) {
            sum = sum.add(BigInteger.valueOf(results[i]));
        }
        return sum.divide(BigInteger.valueOf(results.length)).longValue();
    }
}
