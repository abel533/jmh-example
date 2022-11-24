package printmap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HashMapPrint {
    public static Field tableField;

    public static Class<?> nodeClass;
    public static Field nextField;

    public static Class<?> treeNodeClass;
    public static Field leftField;
    public static Field rightField;
    public static Field redField;

    public static final int speed = 1000;

    static {
        try {
            tableField = HashMap.class.getDeclaredField("table");
            tableField.setAccessible(true);

            nodeClass = Class.forName("java.util.HashMap$Node");
            nextField = nodeClass.getDeclaredField("next");
            nextField.setAccessible(true);

            treeNodeClass = Class.forName("java.util.HashMap$TreeNode");
            leftField = treeNodeClass.getDeclaredField("left");
            rightField = treeNodeClass.getDeclaredField("right");
            redField = treeNodeClass.getDeclaredField("red");
            leftField.setAccessible(true);
            rightField.setAccessible(true);
            redField.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PrintTreeNode convert(Object node) throws Exception {
        if (!treeNodeClass.isInstance(node)) {
            return null;
        }
        String color = ((boolean)redField.get(node)) ? "R:" : "B:";
        return new PrintTreeNode(color + ((Map.Entry) node).getValue(), convert(leftField.get(node)), convert(rightField.get(node)));
    }

    private static class PrintTreeNode {
        Object value;
        PrintTreeNode left;
        PrintTreeNode right;

        public PrintTreeNode(Object value, PrintTreeNode left, PrintTreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
            if (right != null) {
                right.toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
            }
            sb.append(prefix).append(isTail ? "└── " : "┌── ").append(value.toString()).append("\n");
            if (left != null) {
                left.toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
            }
            return sb;
        }

        @Override
        public String toString() {
            return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
        }
    }

    public static void printTableLength(String title, HashMap map) throws Exception {
        Map.Entry<MapTest.Key, Integer>[] tables = (Map.Entry<MapTest.Key, Integer>[]) tableField.get(map);
        System.out.println(title + (tables != null ? tables.length : 0));
    }

    public static void print(HashMap map) throws Exception {
        Map.Entry<MapTest.Key, Integer>[] tables = (Map.Entry<MapTest.Key, Integer>[]) tableField.get(map);
        for (int i = 0; i < map.size(); i++) {
            Map.Entry<MapTest.Key, Integer> entry = tables[i];
            if (entry == null) {
                continue;
            }
            if (treeNodeClass.isInstance(entry)) {
                System.out.println("table[" + i + "] " + entry.getKey());
                System.out.println(convert(entry));
            } else if (nodeClass.isInstance(entry)) {
                System.out.print("table[" + i + "] " + entry.getKey() + " -> " + entry.getValue());
                Map.Entry<MapTest.Key, Integer> next = (Map.Entry<MapTest.Key, Integer>) nextField.get(entry);
                while (next != null) {
                    System.out.print(" -> " + next.getValue());
                    next = (Map.Entry<MapTest.Key, Integer>) nextField.get(next);
                }
                System.out.println();
            }
        }
        Thread.sleep(speed);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
