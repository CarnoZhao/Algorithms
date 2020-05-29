import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MyString implements Comparable<MyString> {
    private static final int ASCII = 256;
    private char[] value;
    private int offset;
    private int length;
    // private int hash;

    public int length() {
        return length;
    }

    public int charAt(int i) {
        return i < length ? value[i + offset] : -1;
    }

    private MyString(int offset, int length, char[] value) {
        this.offset = offset;
        this.length = length;
        this.value = value;
    }

    private MyString(char[] value) {
        this.offset = 0;
        this.length = value.length;
        this.value = value;
    }

    public MyString substring(int from, int to) {
        return new MyString(offset + from, to - from, value);
    }

    public MyString reverse() {
        char[] newvalue = new char[length];
        for (int i = 0; i < length; i ++) {
            newvalue[length - 1 - i] = (char) charAt(i);
        }
        return new MyString(newvalue);
    }

    public MyString keyIndexSort() {
        int[] cnt = new int[ASCII + 1];
        char[] newvalue = new char[length];
        for (int i = 0; i < ASCII + 1; i ++) cnt[i] = 0;
        for (int i = 0; i < length; i ++) {
            cnt[charAt(i) + 1] ++;
        }
        for (int i = 0; i < ASCII; i ++) {
            cnt[i + 1] += cnt[i];
        }
        for (int i = 0; i < length; i ++) {
            char c = (char) charAt(i);
            newvalue[cnt[c]] = c;
            cnt[c] ++;
        }
        return new MyString(newvalue);
    }

    public MyString[] sortLSD(MyString[] strs) {
        int len = strs.length;
        int width = strs[0].length;
        int[] cnt = new int[ASCII + 1];
        MyString[] newstrs = new MyString[len];
        for (int j = width - 1; j >= 0; j --) {
            for (int i = 0; i < ASCII + 1; i ++) cnt[i] = 0;
            for (int i = 0; i < len; i ++)       cnt[strs[i].charAt(j) + 1] ++;
            for (int i = 0; i < ASCII; i ++)     cnt[i + 1] += cnt[i];
            for (int i = 0; i < len; i ++) {
                newstrs[cnt[strs[i].charAt(j)]] = strs[i];
                cnt[strs[i].charAt(j)] ++;
            }
            for (int i = 0; i < len; i ++) strs[i] = newstrs[i];
        }    
        return newstrs;
    }

    public MyString[] sortMSD(MyString[] strs) {
        int len = strs.length;
        MyString[] newstrs = new MyString[len];
        sortMSDSub(strs, newstrs, 0, len, 0);    
        return newstrs;
    }

    private void sortMSDSub(MyString[] strs, MyString[] newstrs, int start, int end, int j) {
        if (start >= end - 1) return;
        int[] cnt = new int[ASCII + 2];
        for (int i = 0; i < ASCII + 2; i ++) cnt[i] = 0;
        for (int i = start; i < end; i ++)       cnt[strs[i].charAt(j) + 2] ++;
        for (int i = 0; i < ASCII + 1; i ++)     cnt[i + 1] += cnt[i];
        for (int i = start; i < end; i ++) {
            newstrs[cnt[strs[i].charAt(j) + 1] + start] = strs[i];
            cnt[strs[i].charAt(j) + 1] ++;
        }
        for (int i = start; i < end; i ++) strs[i] = newstrs[i];
        for (int i = 0; i < ASCII + 1; i ++) {
            sortMSDSub(strs, newstrs, start + cnt[i], start + cnt[i + 1], j + 1);
        }
    }

    public int compareTo(MyString that) {
        int len = Math.min(this.length, that.length);
        for (int i = 0; i < len; i ++) {
            if (this.charAt(i) < that.charAt(i)) {
                return -1;
            } else if (this.charAt(i) > that.charAt(i)) {
                return 1;
            }
        }
        if (this.length < that.length) {
            return -1;
        } else if (this.length > that.length) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        String ret = "";
        for (int i = 0; i < length; i ++) {
            ret += (char) charAt(i);
        }
        return ret;
    }

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(".\\Week8_String\\test.txt"));
            String line;
            List<MyString> strs = new ArrayList<>();
            char[] value;
            while ((line = in.readLine()) != null) {
                value = new char[line.length()];
                for (int i = 0; i < line.length(); i ++) value[i] = line.charAt(i);
                strs.add(new MyString(value));
            }

            MyString str = strs.get(0);
            System.out.println("reverse: " + str.reverse());
            System.out.println("keyIndexSort: " + str.keyIndexSort());
            
            MyString[] arrstrs = new MyString[strs.size()];
            strs.toArray(arrstrs);
            System.out.println("LSD sort:");
            for (MyString s: str.sortLSD(arrstrs)) {
                System.out.println(s);
            }
            System.out.println("------");

            arrstrs = new MyString[strs.size()];
            strs.toArray(arrstrs);
            System.out.println("MSD sort:");
            for (MyString s: str.sortMSD(arrstrs)) {
                System.out.println(s);
            }
            System.out.println("------");

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}