import java.io.BufferedReader;
import java.io.FileReader;

public class Tries<Value> {
    private static final int R = 256;
    private Node root = new Node();

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }


    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }
    
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public Value get(String key) {
        Node tmp = root;
        for (int i = 0; i < key.length() && tmp != null; i ++) {
            tmp = tmp.next[key.charAt(i)];
        }
        if (tmp == null) return null;
        return (Value) tmp.val;
    }

    public void remove(String key) {
        root = remove(root, key, 0);
    }

    private Node remove(Node x, String key, int idx) {
        if (x == null) return null;
        if (idx == key.length()) {
            x.val = null;
        } else {  
            x.next[key.charAt(idx)] = remove(x.next[key.charAt(idx)], key, idx + 1);
        }
        for (Node nex: x.next) {
            if (nex != null) {
                return x;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Tries<Integer> t = new Tries<>();
            BufferedReader in = new BufferedReader(new FileReader(".\\Week8_String\\test2.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("add")) {
                    t.put(line.split(" ")[1], Integer.parseInt(line.split(" ")[2]));
                } else if (line.startsWith("get")) {
                    System.out.println(t.get(line.split(" ")[1]));
                } else if (line.startsWith("remove")) {
                    t.remove(line.split(" ")[1]);
                }
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}