package org.sample.string;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringInternTest {
    private static String[] cities = {new String("A"), new String("B"), new String("C"),
            new String("D"), new String("E"), new String("F"), new String("G")};
    private static String[] regions = {new String("a"), new String("b"), new String("c"),
            new String("d"), new String("e"), new String("f"), new String("g")};
    private static String[] codes = {new String("aa"), new String("bb"), new String("cc"),
            new String("dd"), new String("ee"), new String("ff"), new String("gg")};

    public static void main(String[] args) throws IOException {
        int size = 1000_0000;
        System.in.read();
        testNewString(size);
        System.in.read();
    }

    private static void testNewString(int size){
        Random random = new Random();
        List<Location> locations = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            locations.add(new Location(
                    cities[random.nextInt(cities.length)] + cities[random.nextInt(cities.length)],
                    regions[random.nextInt(regions.length)] + regions[random.nextInt(regions.length)],
                    codes[random.nextInt(codes.length)] + codes[random.nextInt(codes.length)],
                    random.nextDouble(),
                    random.nextDouble()));
        }
        System.out.println(locations.size());
    }

    private static void testStringIntern(int size){
        Random random = new Random();
        List<Location> locations = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            locations.add(new Location(
                    (cities[random.nextInt(cities.length)] + cities[random.nextInt(cities.length)]).intern(),
                    (regions[random.nextInt(regions.length)] + regions[random.nextInt(regions.length)]).intern(),
                    (codes[random.nextInt(codes.length)] + codes[random.nextInt(codes.length)]).intern(),
                    random.nextDouble(),
                    random.nextDouble()));
        }
        System.out.println(locations.size());
    }

}
