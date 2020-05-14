import java.util.Scanner;
// O(N), less waste space
public class ResizeArrayStack {
    private String[] S;
    private int cnt = 0;

    public ResizeArrayStack() {
        S = new String[1];
    }

    public void push(String val) { // O(N)
        if (cnt == S.length) {
            resize(2 * S.length);
        }
        S[cnt] = val;
        cnt ++;  
    }

    public String pop() { // O(N)
        if (empty()) {
            System.out.println("Invalid, empty stack!");
            return "";
        }
        String ret = S[cnt - 1];
        cnt --;
        if (cnt * 4 <= S.length) {
            resize(S.length / 2);
        }
        return ret;
    }

    private void resize(int size) {
        String[] newS = new String[size];
        for (int i = 0; i < cnt; i ++) {
            newS[i] = S[i];
        }
        S = newS;
    }

    public boolean empty() {
        return cnt == 0;
    }

    public void print() {
        for (int i = 0; i < cnt; i ++) {
            System.out.print(S[i] + " ");
        }
        System.out.println("END, cnt = " + cnt + ", length = " + S.length);
    }

    public static void main(String[] args) {
        ResizeArrayStack st = new ResizeArrayStack();
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