import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {
    Map<Integer, Set<Integer>> dic = new HashMap<>();

    public void addEdge(int i, int j) {
        if (!dic.containsKey(i)) dic.put(i, new HashSet<>());
        if (!dic.containsKey(j)) dic.put(j, new HashSet<>());

        dic.get(i).add(j);
        dic.get(j).add(i);
    }

    public Iterable<Integer> adj(int i) {
        if (!dic.containsKey(i)) return new HashSet<>();
        return dic.get(i);
    }

    public int V() {
        return dic.size();
    }

    public int E() {
        int cnt = 0;
        for (int i : dic.keySet()) {
            cnt += dic.get(i).size();
        }
        return cnt / 2;
    }

    public int degree(int i) {
        return dic.get(i).size();
    }

    public boolean isConnected(int i, int j) {
        return dic.get(i).contains(j);
    }

    public void removeEdge(int i, int j) {
        if (!isConnected(i, j))
            return;
        dic.get(i).remove(j);
        dic.get(j).remove(i);
    }

    public void print() {
        for (int i : dic.keySet()) {
            for (int j : adj(i)) {
                if (j <= i) continue;
                System.out.println(i + "-" + j);
            }
        }
    }
    
    public Iterable<Integer> depthFirstSearch(int x) {
        Stack<Integer> st = new Stack<>();
        st.push(x);
        HashSet<Integer> vis = new HashSet<>();
        vis.add(x);
        return new Iterable<Integer>(){
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    public boolean hasNext() {
                        return !st.empty();
                    }

                    public Integer next() {
                        Integer ret = st.pop();
                        for (int nex: adj(ret)) if (!vis.contains(nex)) {
                            st.push(nex);
                            vis.add(nex);
                        }
                        return ret;
                    }
                };
            }
        };
    }

    public Iterable<Integer> breadthFirstSearch(int x) {
        Queue<Integer> st = new LinkedList<>();
        st.add(x);
        HashMap<Integer, Boolean> vis = new HashMap<>();
        vis.put(x, true);
        return new Iterable<Integer>(){
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    public boolean hasNext() {
                        return !st.isEmpty();
                    }

                    public Integer next() {
                        Integer ret = st.remove();
                        for (int nex: adj(ret)) if (!vis.containsKey(nex) || !vis.get(nex)) {
                            st.add(nex);
                            vis.put(nex, true);
                        }
                        return ret;
                    }
                };
            }
        };
    }

    public int connectedComponent() {
        Stack<Integer> st = new Stack<>();
        HashMap<Integer, Boolean> vis = new HashMap<>();
        int cnt = 0;
        for (int i: dic.keySet()) vis.put(i, false);
        for (int i: dic.keySet()) {
            if (vis.get(i)) continue;
            vis.put(i, true);
            st.push(i);
            while (!st.empty()) {
                int x = st.pop();
                for (int j: dic.get(x)) {
                    if (!vis.get(j)) { st.push(j); vis.put(j, true); }
                }
            }
            cnt ++;
        }
        return cnt;
    }


    public void dfsPrint(int i) {
        for (int j: depthFirstSearch(i)) {
            System.out.print(j + " -> ");
        }
        System.out.println();
    }

    public void bfsPrint(int i) {
        for (int j: breadthFirstSearch(i)) {
            System.out.print(j + " -> ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        try {
            BufferedReader in = new BufferedReader(new FileReader(".\\Week7_Graph\\test1.txt"));
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
            g.dfsPrint(1);
            g.bfsPrint(1);
            System.out.println(g.connectedComponent());
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
}