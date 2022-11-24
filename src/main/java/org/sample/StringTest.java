package org.sample;

import java.util.HashMap;
import java.util.Map;

public class StringTest {

    public static void main(String[] args) {
        int num = 2048;
        String str1 = "Java";
        String str2 = new String("Java");
        String str3 = str2.intern();
        System.out.println(str1 == str2);
        System.out.println(str2 == str3);
        System.out.println(str1 == str3);

        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        System.out.println(map);
    }

}

