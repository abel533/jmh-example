package org.sample.collection;

import java.util.ArrayList;
import java.util.Iterator;

public class ForTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("a");
        list.add("b");
        list.add("b");
        list.add("c");
        list.add("c");
        Iterator<String> it = list.iterator();
        while(it.hasNext()) {
            if (it.next().equals("b")) {
                it.remove();
            }
        }


        list.stream().forEach(System.out::println);
    }







    /*
    //        Iterator<String> it = list.iterator();
//        while(it.hasNext()) {
//            if (it.next().equals("b")) {
//                it.remove();
//            }
//        }
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals("b")) {
                list.remove(i);
            }
        }
        list.stream().forEach(System.out::println);
     */
}
