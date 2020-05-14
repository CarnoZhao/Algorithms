import java.util.Scanner;

public class ResizeArrayQueue {
    private String[] S;
    private int head = 0, tail = 0;

    public ResizeArrayQueue() {
        S = new String[1];
    }

    public void push(String val) { // O(N)
        if (tail - head == S.length) {
            resize(2 * S.length);
        }
        S[tail] = val;
        tail ++;  
    }

    public String pop() { // O(N)
        if (empty()) {
            System.out.println("Invalid, empty stack!");
            return "";
        }
        String ret = S[head];
        head ++;
        if ((tail - head) * 4 <= S.length) {
            resize(S.length / 2);
        }
        return ret;
    }

    private void resize(int size) {
        String[] newS = new String[size];
        for (int i = head; i < tail; i ++) {
            newS[i - head] = S[i];
        }
        tail = tail - head;
        head = 0;
        S = newS;
    }

    public boolean empty() {
        return tail - head == 0;
    }

    public void print() {
        for (int i = head; i < tail; i ++) {
            System.out.print(S[i] + " ");
        }
        System.out.println("END, cnt = " + (tail - head) + ", length = " + S.length);
    }

    public static void main(String[] args) {
        ResizeArrayQueue st = new ResizeArrayQueue();
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