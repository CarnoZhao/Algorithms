
public class WeightQuickUnion {
    private int[] id;
    private int[] size;

    public WeightQuickUnion(int N) {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i ++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int p) {
        while (p != id[p]) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        p = root(p); q = root(q);
        if (size[p] < size[q]) {
            id[p] = q;
            size[q] += size[p];
        } else {
            id[q] = p;
            size[p] += size[q];
        }
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }
}