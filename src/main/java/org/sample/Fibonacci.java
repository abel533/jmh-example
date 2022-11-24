package org.sample;

import cn.hutool.core.date.StopWatch;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark                  (size)   Mode  Cnt          Score   Error  Units
 * Fibonacci.iterator             10  thrpt    2  715495833.602          ops/s
 * Fibonacci.iterator             20  thrpt    2  597281688.956          ops/s
 * Fibonacci.iterator             30  thrpt    2  462774993.827          ops/s
 * Fibonacci.recursion            10  thrpt    2   22884630.181          ops/s
 * Fibonacci.recursion            20  thrpt    2     169182.035          ops/s
 * Fibonacci.recursion            30  thrpt    2       1301.994          ops/s
 * Fibonacci.recursion_cache      10  thrpt    2   73085300.902          ops/s
 * Fibonacci.recursion_cache      20  thrpt    2   38134796.983          ops/s
 * Fibonacci.recursion_cache      30  thrpt    2   24938424.314          ops/s
 */
@Threads(6)
@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(1)
public class Fibonacci {

    @Param({"10", "20", "30"})
    private int num;

    private long recursion(long n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        return recursion(n - 1) + recursion(n - 2);
    }

    public static class Cache {
        long[] caches;
        int num;

        public Cache(int num) {
            this.num = num;
            caches = new long[num + 1];
        }

        public long calculate() {
            return recursion(num);
        }

        private long recursion(int n) {
            if (caches[n] != 0) {
                return caches[n];
            }
            if (n <= 0) {
                return 0;
            } else if (n == 1) {
                return 1;
            }
            long val = recursion(n - 1) + recursion(n - 2);
            caches[n] = val;
            return val;
        }

    }

    private long recursion_cache(int n) {
        return new Cache(n).calculate();
    }

    private long iterator(long n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        long f0 = 0, f1 = 1, f2 = 0;
        for (long i = 1; i < n; i++) {
            f2 = f0 + f1;
            f0 = f1;
            f1 = f2;
        }
        return f2;
    }

    @Benchmark
    public long recursion() {
        return recursion(num);
    }

    @Benchmark
    public long recursion_cache() {
        return recursion_cache(num);
    }

    @Benchmark
    public long iterator() {
        return iterator(num);
    }

    public static void main(String[] args) {
        testStopWatch(10);
        testStopWatch(20);
        testStopWatch(30);
        testStopWatch(40);
        testStopWatch(50);
//        testJmh();
//        Fibonacci fibonacci = new Fibonacci();
//        for (int i = 0; i < 20; i++) {
//            System.out.println(fibonacci.iterator(i));
//            System.out.println(fibonacci.recursion_cache(i));
//        }
    }

    public static void testJmh() {
        org.openjdk.jmh.runner.options.Options opt = new OptionsBuilder()
                .include(Fibonacci.class.getSimpleName())
                .shouldDoGC(true)
                .resultFormat(ResultFormatType.JSON)
                .result(System.currentTimeMillis() + ".json")
                .forks(1)
                .build();

        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testStopWatch() {
        for (int i = 10; i < 50; i++) {
            testStopWatch(i);
        }
    }

    public static void testStopWatch(int num) {
        Fibonacci fibonacci = new Fibonacci();
        StopWatch stopWatch = new StopWatch("斐波那契数列，计算: " + num);
        if (num < 50) {
            //50时需要1分钟时间
            stopWatch.start("递归算法");
            System.out.println(fibonacci.recursion(num));
            stopWatch.stop();
        }
        stopWatch.start("递归缓存算法");
        System.out.println(fibonacci.recursion_cache(num));
        stopWatch.stop();
        stopWatch.start("迭代算法");
        System.out.println(fibonacci.iterator(num));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
