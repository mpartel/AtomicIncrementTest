Benchmarks atomic increment on Java's [AtomicLongArray](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/atomic/AtomicLongArray.html) against a custom implementation.

## Running ##

    ant run

Currently only works on Linux/amd64.
Modify build.xml if you don't have a JVM at `/usr/lib/jvm/java-1.6.0-openjdk-amd64`.

## Results ##

The custom implementation is consistently 4-6 times faster with 4 threads doing 1 million increments each. The JIT is, I believe, properly warmed up for both.

There is a (I believe minor) caveat: the benchmark currently only tests arrays of size 1.

## Theory ##

The custom implementation uses amd64's atomic increment command `lock; incq (%r)`.
[Java's implementation](http://www.jarvana.com/jarvana/view/org/codehaus/jsr166-mirror/jsr166/1.7.0/jsr166-1.7.0-sources.jar!/java/util/concurrent/atomic/AtomicLongArray.java?format=ok) uses a compare-and-swap loop and has significantly more method call indirection, though the JVM might JIT some of that out.
Both implementations range check their indexes.
