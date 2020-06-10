import java.io.BufferedReader;
import java.io.FileReader;

public class TernarySearchTries<Value> {
    private static final int R = 256;
    private Node root;

    private static class Node {
        private Object val;
        private char c;
        private Node left;
        private Node right;
        private Node mid;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }
    
    private Node put(Node x, String key, Value val, int idx) {
        char c = key.charAt(idx);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c) x.left = put(x.left, key, val, idx);
        else if (c > x.c) x.right = put(x.right, key, val, idx);
        else if (idx < key.length() - 1) x.mid = put(x.mid, key, val, idx + 1);
        else x.val = val;
        return x;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int idx) {
        if (x == null) return null;
        char c = key.charAt(idx);
        if (c < x.c) return get(x.left, key, idx);
        else if (c > x.c) return get(x.right, key, idx);
        else if (idx < key.length() - 1) return get(x.mid, key, idx + 1);
        else return x;
    }

    public static void main(String[] args) {
        try {
            TernarySearchTries<Integer> t = new TernarySearchTries<>();
            BufferedReader in = new BufferedReader(new FileReader(".\\Week8_String\\test2.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("add")) {
                    t.put(line.split(" ")[1], Integer.parseInt(line.split(" ")[2]));
                } else if (line.startsWith("get")) {
                    System.out.println(t.get(line.split(" ")[1]));
                } else if (line.startsWith("remove")) {
                    // t.remove(line.split(" ")[1]);
                }
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}