package fi.helsinki.cs.atomicincrementtest;

public class MultithreadingBenchmark implements Benchmark {

    private final int numThreads;
    private final Runnable task;
    private long result;

    public MultithreadingBenchmark(int numThreads, Runnable task) {
        this.numThreads = numThreads;
        this.task = task;
    }
    
    @Override
    public void run() {
        long startTime = System.nanoTime();
        
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; ++i) {
            threads[i] = new Thread(task);
            threads[i].setDaemon(true);
            threads[i].start();
        }

        try {
            for (int i = 0; i < numThreads; ++i) {
                threads[i].join();
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        
        result = System.nanoTime() - startTime;
    }

    @Override
    public long getResult() {
        return result;
    }
}
