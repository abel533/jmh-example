package org.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Threads(6)
@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class StringIntern {

    @Param({"100", "10000"})
    private int size;

    private StringInterner str;
    private CHMInterner chm;
    private HMInterner hm;

    @Setup
    public void setup() {
        str = new StringInterner();
        chm = new CHMInterner();
        hm = new HMInterner();
    }

    public static class StringInterner {
        public String intern(String s) {
            return s.intern();
        }
    }

    @Benchmark
    public void intern(Blackhole bh) {
        for (int c = 0; c < size; c++) {
            bh.consume(str.intern("String" + c));
        }
    }

    public static class CHMInterner {
        private final Map<String, String> map;

        public CHMInterner() {
            map = new ConcurrentHashMap<>();
        }

        public String intern(String s) {
            String exist = map.putIfAbsent(s, s);
            return (exist == null) ? s : exist;
        }
    }

    @Benchmark
    public void chm(Blackhole bh) {
        for (int c = 0; c < size; c++) {
            bh.consume(chm.intern("String" + c));
        }
    }

    public static class HMInterner {
        private final Map<String, String> map;

        public HMInterner() {
            map = new HashMap<>();
        }

        public String intern(String s) {
            String exist = map.putIfAbsent(s, s);
            return (exist == null) ? s : exist;
        }
    }

    @Benchmark
    public void hm(Blackhole bh) {
        for (int c = 0; c < size; c++) {
            bh.consume(hm.intern("String" + c));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringIntern.class.getName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}