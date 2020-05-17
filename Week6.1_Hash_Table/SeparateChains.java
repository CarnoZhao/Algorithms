
public class SeparateChains {
    private int M = 5;
    private Node[] st = new Node[M];

    private static class Node {
        private Object key;
        private Object val;
        private Node next;
        Node(Object key, Object val) {
            this.key = key;
            this.val = val;
        }
    }

    private int hash(Object key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Object get(Object key) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next) {
            if (key.equals(x.key)) return x.val;
        }
        return null;
    }

    public void set(Object key, Object val) {
        int i = hash(key);
        if (st[i] == null) {
            st[i] = new Node(key, val);
        } else {
            Node x = st[i];
            while (x.next != null) {
                if (key.equals(x.key)) {
                    x.val = val;
                    return;
                }
                x = x.next;
            }
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
            x.next = new Node(key, val);
        }
    }

    public void print() {
        for (int i = 0; i < M; i ++) {
            Node x = st[i];
            if (x == null) continue;
            System.out.print(i + ": ");
            while (x != null) {
                System.out.print("(" + x.key + ", " + x.val + ") -> ");
                x = x.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SeparateChains ht = new SeparateChains();
        ht.set("fasd", 1);
        ht.set(2, 10);
        ht.print();
        ht.set(2, 5);
        ht.set(2.31, "fasdfa");
        System.out.println(ht.get(2.31));
        ht.print();
    }
}