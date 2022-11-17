package org.sample;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Threads(10)
@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Mode.All)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class JsonTest {
    private static final String jsonStr = "{\"name\":\"名字\", \"age\": 20}";
    private static final Gson gson = new Gson();

    public static class User {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    @Benchmark
    public void testGson() {
        User user = gson.fromJson(jsonStr, User.class);
        print(user);
    }

    @Benchmark
    public void testFastjson() {
        User user = JSON.parseObject(jsonStr, User.class);
        print(user);
    }

    private void print(User user) {

    }

}
