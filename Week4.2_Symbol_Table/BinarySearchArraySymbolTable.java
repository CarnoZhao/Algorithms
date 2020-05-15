import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BinarySearchArraySymbolTable<Key extends Comparable<Key>, Value> {
    List<Key> keys = new ArrayList<>();
    List<Value> values = new ArrayList<>();

    BinarySearchArraySymbolTable() {}

    public void set(Key key, Value val) {
        int idx = rank(key);
        if (idx == size()) {
            keys.add(key);
            values.add(val);
        } else if (keys.get(idx).equals(key)) {
            values.set(idx, val);
        } else {
            keys.add(idx, key);
            values.add(idx, val);
        }
    }

    public Value get(Key key) {
        if (empty())
            return null;
        int idx = rank(key);
        if (keys.get(idx).equals(key)) {
            return values.get(idx);
        } else {
            return null;
        }
    }

    public int rank(Key key) {
        int l = 0, r = keys.size();
        while (l < r) {
            int m = (l + r) / 2;
            if (keys.get(m).compareTo(key) < 0) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    public void delete(Key key) {
        if (empty())
            return;
        int idx = rank(key);
        if (keys.get(idx).equals(key)) {
            keys.remove(idx);
            values.remove(idx);
        }
    }

    public boolean empty() {
        return keys.size() == 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public int size() {
        return keys.size();
    }

    public Key min() {
        return keys.get(0);
    }

    public Key max() {
        return keys.get(size() - 1);
    }

    public Key floor(Key key) {
        if (empty())
            return null;
        int idx = rank(key);
        return keys.get(idx);
    }

    public Key ceiling(Key key) {
        if (empty())
            return null;
        int idx = rank(key);
        if (keys.get(idx).equals(key)) {
            return key;
        } else if (idx + 1 < size()) {
            return keys.get(idx + 1);
        } else {
            return null;
        }
    }

    public Key select(int k) {
        return keys.get(k);
    }

    public void deleteMin() {
        keys.remove(0);
        values.remove(0);
    }

    public void deleteMax() {
        keys.remove(size() - 1);
        values.remove(size() - 1);
    }

    public int size(Key l, Key r) {
        int i = rank(l);
        int j = rank(r);
        return j - i + (keys.get(j).equals(r) ? 1 : 0);
    }

    public Iterable<Key> keys(Key l, Key r) {
        int lidx = rank(l);
        int tmp = rank(r);
        if (tmp < size() && !keys.get(tmp).equals(r)) {
            tmp --;
        }
        int ridx = tmp;
        return new Iterable<Key>() {
            public Iterator<Key> iterator() {
                return new Iterator<Key>() {
                    int i = lidx;
                    int j = ridx;

                    public boolean hasNext() {
                        return i <= j;
                    }

                    public Key next() {
                        return keys.get(i ++);
                    }
                };
            }
        };
    }

    public Iterable<Key> keys() {
        return new Iterable<Key>() {
            public Iterator<Key> iterator() {
                return new Iterator<Key>() {
                    int j = size() - 1;
                    int k = 0;

                    public boolean hasNext() {
                        return k <= j;
                    }

                    public Key next() {
                        return keys.get(k ++);
                    }
                };
            }
        };
    }

    public void print() {
        System.out.println("-----");
        for (Key key: keys()) {
            System.out.println(key + " : " + get(key));
        }
        System.out.println("-----");
    }

    public void print(Key l, Key r) {
        System.out.println("-----");
        for (Key key: keys(l, r)) {
            System.out.println(key + " : " + get(key));
        }
        System.out.println("-----");
    }

    public static void main(String[] args) {
        BinarySearchArraySymbolTable<String, Integer> st = new BinarySearchArraySymbolTable<>();
        // Scanner sc = new Scanner(System.in);
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("Week4.2_Symbol_Table\\test.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.startsWith("set")) {
                    String[] fields = line.split(" ");
                    st.set(fields[1], Integer.parseInt(fields[2]));
                } else if (line.startsWith("get")) {
                    Integer ret = st.get(line.split(" ")[1]);
                    System.out.println(ret);
                } else if (line.startsWith("delete")) {
                    st.delete(line.split(" ")[1]);
                } else if (line.startsWith("printRange")) {
                    String[] fields = line.split(" ");
                    st.print(fields[1], fields[2]);
                    System.out.println(st.size(fields[1], fields[2]));
                } else if (line.equals("?")) {
                    break;
                }
                st.print();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}