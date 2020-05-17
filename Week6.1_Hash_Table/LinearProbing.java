
public class LinearProbing {
    private int M = 30001;
    private Object[] keys = new Object[M];
    private Object[] vals = new Object[M];


    private int hash(Object key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Object get(Object key) {
        for (int i = hash(key); i < M && keys[i] != null; i ++) {
            if (key.equals(keys[i])) return vals[i];
        }
        return null;
    }

    public void set(Object key, Object val) {
        int i;
        for (i = hash(key); i < M && keys[i] != null; i ++) {
            if (key.equals(keys[i])) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
    }

    public void print() {
        for (int i = 0; i < M; i ++) {
            if (keys[i] == null) continue;
            System.out.println(keys[i] + ": " + vals[i]);
        }
    }

    public static void main(String[] args) {
        // potential bug: index out of range of array
        LinearProbing ht = new LinearProbing();
        ht.set("fasd", 1);
        ht.set(2, 10);
        ht.print();

        ht.set(2, 5);
        ht.set(2.31, "fasdfa");
        ht.print();

        System.out.println(ht.get(2.31));
    }
}