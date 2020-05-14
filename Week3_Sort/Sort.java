import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sort<Item extends Comparable<Item>> {
    public List<Item> arr = new ArrayList<>();

    public void add(Item a) {
        arr.add(a);
    }

    public void selectionSort() { // best O(N^2), worst O(N^2), mean O(N^2)
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            int mij = i + 1;
            Item mi = null;
            for (int j = i + 1; j < n; j++) {
                if (mi == null || less(arr.get(j), mi)) {
                    mij = j;
                    mi = arr.get(j);
                }
            }
            swap(i, mij);
        }
    }

    public void insertionSort() { // best O(N), worst O(N^2), mean O(N^2)
        int n = arr.size();
        for (int i = 1; i < n; i ++) {
            int j = i;
            while (j > 0 && less(arr.get(j), arr.get(j - 1))) {
                swap(j - 1, j);
                j --;
            }
        }
    }

    public void shellSort(int[] hs) { // unknow
        for (int h: hs) {
            hSort(h);
        }
    }

    public void shuffle() {
        int n = arr.size();
        Random r = new Random();
        for (int i = 1; i < n; i ++) {
            int j = r.nextInt(i);
            swap(i, j);
        }
    }

    private void hSort(int h) {
        int n = arr.size();
        for (int i = h; i < n; i += h) {
            for (int j = i; j >= h && less(arr.get(j), arr.get(j - h)); j -= h)
                swap(j - h, j);
        }
    }

    public boolean isSorted() {
        for (int i = 1; i < arr.size(); i ++) {
            if (less(arr.get(i), arr.get(i - 1))) {
                System.out.println("Not sorted!");
                return false;
            }
        }
        System.out.println("Sorted!");
        return true;
    }

    public void swap(int i, int j) {
        Item tmp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, tmp);
    }

    public boolean less(Item a, Item b) {
        return a.compareTo(b) < 0;
    }

    public void print() {
        for (Item x: arr) {
            System.out.print(x + " ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        Sort<Integer> arr = new Sort<Integer>();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("test.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                for (String x: line.split(", ")) {
                    arr.add(Integer.parseInt(x));
                }
            }
            // selection sort
            arr.selectionSort();
            arr.print();
            arr.isSorted();
            arr.shuffle();

            // insertion sort
            arr.insertionSort();
            arr.print();
            arr.isSorted();
            arr.shuffle();

            // shell sort
            int[] hs = {10, 4, 1}; // good performace of 3x + 1
            arr.shellSort(hs);
            arr.print();
            arr.isSorted();
            arr.shuffle();


            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}