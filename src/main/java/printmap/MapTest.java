package printmap;

import java.util.*;

import static printmap.HashMapPrint.*;

public class MapTest {

    public static class Key implements Comparable<Key>{
        private final int hash;
        private final int value;

        public Key(int hash, int value) {
            this.hash = hash;
            this.value = value;
        }

        @Override
        public int compareTo(Key o) {
            return (value < o.value) ? -1 : ((value == o.value) ? 0 : 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (hash != key.hash) return false;
            return value == key.value;
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }

    public static void main(String[] args) throws Exception {
        HashMap<Key, Integer> map = new HashMap<>();
        int valueSize = 19;
        for (int i = 0; i < valueSize; i++) {
            clearScreen();
            System.out.println("---------------------------------------------");
            printTableLength("table.length before put: ", map);

            map.put(new Key(0, i), i);

            printTableLength("table.length after put : ", map);
            print(map);
        }
    }
}


