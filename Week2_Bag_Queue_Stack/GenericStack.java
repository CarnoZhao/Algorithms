import java.util.Scanner;

public class GenericStack<Item> {
    public class Node {
        public Item val;
        public Node next = null;

        public Node(Item val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    public Node first = null;
    /*
        create array of undefined "Item"
        S = (Item[]) new Object[N];
    */

    public Item pop() {
        if (empty()) {
            System.out.println("Invalid, empty stack!");
            return null;
        }
        Item ret = first.val;
        first = first.next;
        return ret;
    }

    public void push(Item val) {
        Node nex = new Node(val, first);
        first = nex;
    }

    public boolean empty() {
        return first == null;
    }

    public void print() {
        Node tmp = first;
        while (tmp != null) {
            System.out.print(tmp.val + "->");
            tmp = tmp.next;
        } 
        System.out.println("END");
    }

    public static void main(String[] args) {
        GenericStack<String> st = new GenericStack<String>();
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