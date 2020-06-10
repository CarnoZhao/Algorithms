
public class Substring {
    public int KMP(String str, String pat) {
        int n = pat.length();
        int[] next = new int[n];
        kmpNext(pat, next);
        int i = 0, j = 0;
        while (i < str.length() && j < n) {
            if (j == -1 || str.charAt(i) == pat.charAt(j)) {
                i ++; j ++;
            } else {
                j = next[j];
            }
        }
        if (j == n) {
            return i - j;
        } else {
            return -1;
        }
    }

    private void kmpNext(String pat, int[] next) {
        next[0] = -1;
        int i = 0, j = -1;
        while (i < pat.length() - 1) {
            if (j == -1 || pat.charAt(i) == pat.charAt(j)) {
                i ++; j ++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }

    public static void main(String[] args) {
        Substring ss = new Substring();
        String str = "an apple a day keep the doctor away";
        String pat = "apple";
        System.out.println(ss.KMP(str, pat));
    }
}