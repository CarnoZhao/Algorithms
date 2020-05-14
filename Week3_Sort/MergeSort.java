import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MergeSort<Item extends Comparable<Item>> extends Sort<Item> {
    public void mergeSort() { // O(NlogN)
        int n = arr.size();
        List<Item> newarr = new ArrayList<>(n);
        for (int i = 0; i < n; i ++) newarr.add(arr.get(i));
        sort(0, n - 1, newarr);
    }

    public void bottomUpMergeSort() {
        int n = arr.size();
        List<Item> newarr = new ArrayList<>(n);
        for (int i = 0; i < n; i ++) newarr.add(arr.get(i));
        for (int size = 1; size < n; size <<= 1) {
            for (int i = 0; i < n - size; i += size * 2) {
                merge(i, i + size - 1, Math.min(i + size * 2 - 1, n - 1), newarr);
                // print();
            }
        }
    }

    private void sort(int l, int r, List<Item> newarr) {
        if (l >= r) return;
        int m = (l + r) / 2;
        sort(l, m, newarr);
        sort(m + 1, r, newarr);
        merge(l, m, r, newarr);
    }

    private void merge(int l, int m, int r, List<Item> newarr) {
        for (int i = l; i <= r; i ++) {
            newarr.set(i, arr.get(i));
        }
        int i = l, j = m + 1;
        int k = l;
        while (i <= m && j <= r) {
            if (less(newarr.get(i), newarr.get(j))) {
                arr.set(k, newarr.get(i));
                i ++;
            } else {
                arr.set(k, newarr.get(j));
                j ++;
            }
            k ++;
        }
        while (i <= m) {
            arr.set(k, newarr.get(i));
            i ++; k ++;
        }
        while (j <= r) {
            arr.set(k, newarr.get(j));
            j ++; k ++;
        }
    }

    public static void main(String[] args) {
        MergeSort<Integer> arr = new MergeSort<Integer>();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("test.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                for (String x: line.split(", ")) {
                    arr.add(Integer.parseInt(x));
                }
            }
            // recursive merge sort
            arr.shuffle();
            arr.mergeSort();
            arr.print();
            arr.isSorted();

            // bottom up merge sort
            arr.shuffle();
            arr.bottomUpMergeSort();
            arr.print();
            arr.isSorted();

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}