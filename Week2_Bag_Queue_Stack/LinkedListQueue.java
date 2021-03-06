import java.util.Scanner;

public class LinkedListQueue {
    private class Node {
        public String val;
        public Node next = null;

        public Node(String val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    public Node first = null;
    public Node last = null;

    public String pop() {
        if (empty()) {
            System.out.println("Invalid, empty stack!");
            return "";
        }
        String ret = first.val;
        first = first.next;
        return ret;
    }

    public void push(String val) {
        Node nex = new Node(val, null);
        if (last != null) last.next = nex;
        if (first == null) first = nex;
        last = nex;
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
        System.out.println("");
    }

    public static void main(String[] args) {
        LinkedListQueue st = new LinkedListQueue();
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