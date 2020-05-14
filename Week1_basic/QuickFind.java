public class QuickFind {
    private int[] id;

    public QuickFind(int N) {
        id = new int[N];
        for (int i = 0; i < N; i ++) id[i] = i;
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pid = id[p];
        for (int i = 0; i < id.length; i ++) {
            if (id[i] == pid) id[i] = id[q];
        }
    }

    public static void main(String argv[]) {
        System.out.println("Hello");
    }
}