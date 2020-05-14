import java.io.BufferedReader;
import java.io.FileReader;

public class QuickSort<Item extends Comparable<Item>> extends Sort<Item> {
    public void quickSort() {
        shuffle();
        sort(0, arr.size() - 1);
    }

    public void threeWayQuickSort() {
        shuffle();
        threewaysort(0, arr.size() - 1);
    }

    private void threewaysort(int l, int r) {
        if (l >= r) return;
        int i = l, j = r, k = l;
        Item start = arr.get(l);
        while (k <= j) {
            int cmp = arr.get(k).compareTo(start);
            if (cmp < 0) {
                swap(i, k);
                i ++;
                k ++;
            } else if (cmp > 0) {
                swap(k, j);
                j --;
            } else {
                k ++;
            }
        }
        threewaysort(l, i - 1);
        threewaysort(j + 1, r);
    }

    private void sort(int l, int r) {
        if (l >= r) return;
        int m = partition(l, r);
        sort(l, m - 1);
        sort(m + 1, r);
    }

    private int partition(int l, int r) {
        int i = l, j = r;
        while (i < j) {
            while (i < j && !less(arr.get(j), arr.get(l))) j --;
            while (i < j && !less(arr.get(l), arr.get(i))) i ++;
            if (i < j) swap(i, j);
        }
        swap(l, i);
        return i;
    }

    public static void main(String[] args) {
        QuickSort<Integer> arr = new QuickSort<Integer>();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(".\\Week3_Sort\\test.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                for (String x: line.split(", ")) {
                    arr.add(Integer.parseInt(x));
                }
            }
            // quick sort sort
            arr.quickSort();
            arr.print();
            arr.isSorted();
            //three way quick sort
            arr.threeWayQuickSort();
            arr.print();
            arr.isSorted();

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}