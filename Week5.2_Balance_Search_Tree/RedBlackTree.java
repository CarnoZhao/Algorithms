import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private class Node {
        Key key;
        Value val;
        Node left, right;
        boolean color;
        Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.color = RED;
        }
    }

    private Node root;

    public void set(Key key, Value val) {
        root = set(root, key, val);
    }

    private Node set(Node p, Key key, Value val) {
        if (p == null) return new Node(key, val);
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = set(p.left, key, val);
        } else if (cmp == 0) {
            p.val = val;
        } else {
            p.right = set(p.right, key, val);
        }
        if (isRed(p.right) && !isRed(p.left)) p = rotateLeft(p);
        if (isRed(p.left) && isRed(p.left.left)) p = rotateRight(p);
        if (isRed(p.left) && isRed(p.right)) flipColors(p);
        return p;
    }

    public Value get(Key key) {
        Node p = root;
        while (p != null) {
            int cmp = p.key.compareTo(key);
            if (cmp < 0) {
                p = p.right;
            } else if (cmp > 0) {
                p = p.left;
            } else {
                return p.val;
            }
        }
        return null;
    }

    public Iterable<Key> iterator() {
        List<Key> ret = new ArrayList<Key>();
        inorder(root, ret);
        return ret;
    }

    private void inorder(Node p, List<Key> ret) {
        if (p == null) return;
        inorder(p.left, ret);
        ret.add(p.key);
        inorder(p.right, ret);
    }

    public void print() {
        System.out.println("-----");
        for (Key x: iterator()) {
            System.out.println(x + ": " + get(x));
        }
        System.out.println("-----");
    }

    private boolean isRed(Node p) {
        if (p == null) return false;
        return p.color == RED;
    } 

    private Node rotateLeft(Node p) {
        Node q = p.right;
        p.right = q.left;
        q.left = p;
        q.color = p.color;
        p.color = RED;
        return q;
    }

    private Node rotateRight(Node p) {
        Node q = p.left;
        p.left = q.right;
        q.right = p;
        q.color = p.color;
        p.color = RED;
        return q;
    }

    private void flipColors(Node p) {
        p.color = RED;
        p.left.color = BLACK;
        p.right.color = BLACK;
    }

    public static void main(String[] args) {
        RedBlackTree<String, Integer> rbt = new RedBlackTree<String, Integer>();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("Week5.2_Balance_Search_Tree\\test.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.startsWith("set")) {
                    String[] fields = line.split(" ");
                    rbt.set(fields[1], Integer.parseInt(fields[2]));
                } else if (line.startsWith("get")) {
                    Integer ret = rbt.get(line.split(" ")[1]);
                    System.out.println(ret);
                }
                rbt.print();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}