import java.util.Iterator;
import java.util.Scanner;

public class StackIterator<Item> extends GenericStack<Item> implements Iterable<Item> {

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node cur = first;

        public boolean hasNext() {
            return cur != null;
        }

        public void remove() {}

        public Item next() {
            Item val = cur.val;
            cur = cur.next;
            return val;
        }
    }

    public void printIter() {
        for (Item x: this) {
            System.out.print(x + " -> ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        StackIterator<String> st = new StackIterator<String>();
        Scanner sc = new Scanner(System.in);
        String line;
        while (!(line = sc.nextLine()).equals("?")) {
            if (line.startsWith("push")) {
                st.push(line.split(" ")[1]);
            } else if (line.startsWith("pop")) {
                st.pop();
            } else {
                System.out.println("Invalid, not supported operator!");
            }
            st.print();

        }
        sc.close();
    }
}