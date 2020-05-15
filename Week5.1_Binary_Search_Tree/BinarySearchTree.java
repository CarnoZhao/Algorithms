import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private class Node {
        private Key key;
        private Value val;
        private Node left = null, right = null;
        private int cnt;
        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    private Node root;

    public void set(Key key, Value val) {
        root = set(root, key, val);
    }

    private Node set(Node p, Key key, Value val) {
        if (p == null) return new Node(key, val);
        int cmp = p.key.compareTo(key);
        if (cmp < 0) {
            p.right = set(p.right, key, val);
        } else if (cmp == 0) {
            p.val = val;
        } else {
            p.left = set(p.left, key, val);
        }
        p.cnt = size(p.left) + 1 + size(p.right);
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

    public Key min() {
        return minNode(root).key;
    }

    private Node minNode(Node p) {
        if (p == null) return null;
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    public Key max() {
        return maxNode(root).key;
    }

    private Node maxNode(Node p) {
        if (p == null) return null;
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node p, Key key) {
        if (p == null) return p;
        int cmp = p.key.compareTo(key);
        if (cmp < 0) {
            p.right = delete(p.right, key);
        } else if (cmp == 0) {
            if (p.left == null || p.right == null) {
                p = p.left == null ? p.right : p.left;
            } else {
                Node q = minNode(p.right);
                q.right = deleteMin(p.right);
                q.left = p.left;
                p = q;
            }
        }
        p.cnt = size(p.left) + size(p.right) + 1;
        return p;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node p) {
        if (p == null) return p;
        if (p.left == null) {
            return p.right;
        } else {
            p.left = deleteMin(p.left);
            p.cnt = size(p.left) + size(p.right) + 1;
            return p;
        }
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node p) {
        if (p == null) return p;
        if (p.right == null) {
            return p.left;
        } else {
            p.right = deleteMax(p.right);
            p.cnt = size(p.left) + size(p.right) + 1;
            return p;
        }
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    public int rank(Node p, Key key) {
        if (p == null) return 0;
        int cmp = p.key.compareTo(key);
        if (cmp < 0) {
            return size(p.left) + 1 + rank(p.right, key);
        } else if (cmp == 0) {
            return size(p.left);
        } else {
            return rank(p.left, key);
        }
    }

    public Key floor(Key key) {
        Node p = floor(root, key);
        if (p == null) return null;
        return p.key;
    }

    private Node floor(Node p, Key key) {
        if (p == null) return null;
        int cmp = p.key.compareTo(key);
        if (cmp < 0) {
            Node ret = floor(p.right, key);
            return ret == null ? p : ret;
        } else if (cmp == 0) {
            return p;
        } else {
            return floor(p.left, key);
        }
    }

    public Key ceiling(Key key) {
        Node p = ceiling(root, key);
        if (p == null) return null;
        return p.key;
    }

    private Node ceiling(Node p, Key key) {
        if (p == null) return null;
        int cmp = p.key.compareTo(key);
        if (cmp > 0) {
            Node ret = ceiling(p.left, key);
            return ret == null ? p : ret;
        } else if (cmp == 0) {
            return p;
        } else {
            return ceiling(p.right, key);
        }
    }

    private int size(Node p) {
        return p == null ? 0 : p.cnt;
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

    public static void main(String[] args) {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("Week5.1_Binary_Search_Tree\\test.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.startsWith("set")) {
                    String[] fields = line.split(" ");
                    bst.set(fields[1], Integer.parseInt(fields[2]));
                } else if (line.startsWith("get")) {
                    Integer ret = bst.get(line.split(" ")[1]);
                    System.out.println(ret);
                } else if (line.startsWith("delete")) {
                    bst.delete(line.split(" ")[1]);
                } else if (line.startsWith("ceil")) {
                    System.out.println(bst.ceiling(line.split(" ")[1]));
                } else if (line.startsWith("floor")) {
                    System.out.println(bst.floor(line.split(" ")[1]));
                } else if (line.equals("?")) {
                    break;
                }
                bst.print();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}