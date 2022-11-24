package org.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 Benchmark                         (num)   Mode  Cnt         Score   Error  Units
 StringAppend.string_add              10  thrpt    2  20463314.858          ops/s
 StringAppend.string_add              50  thrpt    2   1710215.266          ops/s
 StringAppend.string_add             100  thrpt    2    489930.345          ops/s
 StringAppend.string_buffer           10  thrpt    2  35138192.914          ops/s
 StringAppend.string_buffer           50  thrpt    2   5715884.586          ops/s
 StringAppend.string_buffer          100  thrpt    2   2481157.285          ops/s
 StringAppend.string_buffer_size      10  thrpt    2  31615959.710          ops/s
 StringAppend.string_buffer_size      50  thrpt    2   5773237.196          ops/s
 StringAppend.string_buffer_size     100  thrpt    2   3312252.742          ops/s
 StringAppend.string_builder          10  thrpt    2  42864473.352          ops/s
 StringAppend.string_builder          50  thrpt    2   8227223.248          ops/s
 StringAppend.string_builder         100  thrpt    2   3419966.411          ops/s
 StringAppend.string_builder_size     10  thrpt    2  36541849.403          ops/s
 StringAppend.string_builder_size     50  thrpt    2   9084070.506          ops/s
 StringAppend.string_builder_size    100  thrpt    2   4094552.833          ops/s
 */
@Threads(6)
@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(1)
public class StringAppend {

    @Param({"10", "50", "100"})
    private int num;

    @Benchmark
    public String string_add() {
        String str = "";
        for (int i = 0; i < num; i++) {
            str += i;
        }
        return str;
    }

    @Benchmark
    public String string_builder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

    @Benchmark
    public String string_builder_size() {
        StringBuilder sb = new StringBuilder(num*2);
        for (int i = 0; i < num; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

    @Benchmark
    public String string_buffer() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

    @Benchmark
    public String string_buffer_size() {
        StringBuffer sb = new StringBuffer(num *2);
        for (int i = 0; i < num; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        org.openjdk.jmh.runner.options.Options opt = new OptionsBuilder()
                .include(StringAppend.class.getSimpleName())
                .shouldDoGC(true)
                .resultFormat(ResultFormatType.JSON)
                .result(StringAppend.class.getSimpleName() + ".json")
                .forks(1)
                .build();

        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            throw new RuntimeException(e);
        }
    }

}
