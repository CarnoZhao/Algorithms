import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Max_Priority_Queue<Item extends Comparable<Item>> {
    List<Item> arr = new ArrayList<>();
    int n = 0;
    Max_Priority_Queue() {}

    Max_Priority_Queue(List<Item> arr) {
        for (Item x: arr) {
            this.push(x);
            n ++;
        }
    }

    public void push(Item val) {
        arr.add(val);
        swim(n);
        n ++;
    }

    public Item pop() {
        if (this.empty()) {
            System.out.println("Empty!");
            return null;
        }
        Item ret = arr.get(0);
        arr.set(0, arr.get(n - 1));
        arr.remove(n - 1);
        n --;
        sink(0, n);
        return ret;
    }

    public boolean empty() {
        return n == 0;
    }

    public Item top() {
        return arr.get(0);
    }

    public void heapify() {
        for (int i = n - 1; i >= 0; i --) {
            swim(i);
        }
    }

    public void heapSort() {
        for (int i = n - 1; i >= 0; i --) {
            swap(i, 0);
            sink(0, i);
        }
    }

    private void swim(int i) {
        while (i > 0 && less((i - 1) / 2, i)) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void sink(int i, int N) {
        while (2 * i + 1 < N) {
            int j = 2 * i + 1;
            if (j < N - 1 && less(j, j + 1)) j ++;
            if (!less(i, j)) break;
            swap(i, j);
            i = j;
        }
    }

    private void swap(int i, int j) {
        Item tmp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, tmp);
    }

    private boolean less(int i, int j) {
        return arr.get(i).compareTo(arr.get(j)) < 0;
    }

    public void print() {
        List<Item> tmp = new ArrayList<>();
        while (!this.empty()) {
            System.out.print(this.top() + "->");
            tmp.add(this.pop());
        }
        for (Item x: tmp) this.push(x);
        System.out.println();
    }

    public void printArr() {
        for (int i = 0; i < n; i ++) {
            System.out.print(arr.get(i) + "->");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Max_Priority_Queue<String> heap = new Max_Priority_Queue<String>();
        Scanner sc = new Scanner(System.in);
        String line;
        while (!(line = sc.nextLine()).equals("?")) {
            if (line.startsWith("push")) {
                heap.push(line.split(" ")[1]);
            } else if (line.startsWith("pop")) {
                heap.pop();
            } else {
                System.out.println("Invalid, not supported operator!");
            }
            System.out.print("Pri Queue: ");
            heap.print();
            // heap sort
            System.out.print("Heap sort: ");
            heap.heapSort();
            heap.printArr();
            heap.heapify();
        }
        sc.close();
    }
}