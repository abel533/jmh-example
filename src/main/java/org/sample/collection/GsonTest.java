package org.sample.collection;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class GsonTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println(json);
    }
}
