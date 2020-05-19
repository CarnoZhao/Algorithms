import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DirectedGraph extends Graph{
    public void addEdge(int x, int y) {
        if (!dic.containsKey(x)) dic.put(x, new HashSet<>());
        dic.get(x).add(y);
    }

    public void removeEdge(int x, int y) {
        if (!dic.containsKey(x)) return;
        dic.get(x).remove(y);
    }

    private void reverse() {
        HashMap<Integer, Set<Integer>> newdic = new HashMap<>();
        for (int x: dic.keySet()) {
            for (int y: adj(x)) {
                if (!newdic.containsKey(y)) newdic.put(y, new HashSet<>());
                newdic.get(y).add(x);
            }
        }
        dic = newdic;
    }

    public void print() {
        for (int x: dic.keySet()) {
            for (int y: dic.get(x)) {
                System.out.println(x + "->" + y);
            }
        }
    }

    public boolean isCycle() {
        Stack<Integer> st = new Stack<>();
        Set<Integer> vis = new HashSet<>();
        for (int x: dic.keySet()) {
            if (vis.contains(x)) continue;
            st.push(x);
            while (!st.empty()) {
                int y = st.pop();
                for (int z: adj(y)) {
                    if (z == x) return true;
                    if (vis.contains(z)) continue;
                    st.push(z);
                    vis.add(z);
                }
            }
        }
        return false;
    }

    public Stack<Integer> topologicalSort(boolean checkCycle) {
        if (checkCycle && isCycle()) {
            System.out.println("Cycle! No sort!");
            return null;
        }
        Stack<Integer> ret = new Stack<>();
        Set<Integer> vis = new HashSet<>();
        for (int x: dic.keySet()) {
            if (!vis.contains(x)) {
                vis.add(x);
                topoDFS(x, vis, ret);
            }
        }
        System.out.println(ret.toString());
        return ret;
    }

    public void strongComponent() {
        reverse();
        Stack<Integer> topoOrderST = topologicalSort(false);
        reverse();

        int n = topoOrderST.size();
        int[] topoOrder = new int[n];
        for (int i = 0; i < n; i ++) {
            topoOrder[i] = topoOrderST.pop().intValue();
        }
        
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] scc = new int[n];
        for (int i = 0; i < n; i ++) {
            scc[i] = -1;
            map.put(topoOrder[i], i);
        }

        int cur = 0;
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i ++) {
            if (scc[i] != -1) continue;
            System.out.print(cur + ": ");
            st.push(topoOrder[i]);
            scc[i] = cur;
            while (!st.empty()) {
                int x = st.pop();
                for (int y: adj(x)) {
                    if (scc[map.get(y)] == -1) {
                        scc[map.get(y)] = cur;
                        st.push(y);
                    }
                }
                System.out.print(x + ", ");
            }
            cur ++;
            System.out.println();
        }
    }

    private void topoDFS(int x, Set<Integer> vis, Stack<Integer> ret) {
        for (int y: adj(x)) {
            if (!vis.contains(y)) {
                vis.add(y);
                topoDFS(y, vis, ret);
            }
        }
        ret.push(x);
    }

    public static void main(String[] args) {
        DirectedGraph g = new DirectedGraph();
        try {
            BufferedReader in = new BufferedReader(new FileReader(".\\Week7_Graph\\test2.txt"));
            String line;
            String[] fields;
            while ((line = in.readLine()) != null) {
                fields = line.split(" ");
                if (fields[0].equals("add")) {
                    g.addEdge(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                } else if (fields[0].equals("remove")) {
                    g.removeEdge(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                }
            }
            in.close();
            g.print();
            g.dfsPrint(0);
            g.bfsPrint(0);
            g.topologicalSort(false);
            System.out.println(g.isCycle());
            g.strongComponent();
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
}