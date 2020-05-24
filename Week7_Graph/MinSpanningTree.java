import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import javafx.util.Pair;

public class MinSpanningTree {
    HashMap<Integer, HashMap<Integer, Double>> dic = new HashMap<>();
    Comparator<Pair<Integer, Integer>> cmp = new Comparator<Pair<Integer, Integer>>() {
        @Override
        public int compare(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
            return dic.get(a.getKey()).get(a.getValue()).compareTo(dic.get(b.getKey()).get(b.getValue()));
        }
    };


    void addEdge(int x, int y, double w) {
        if (!dic.containsKey(x)) dic.put(x, new HashMap<>());
        dic.get(x).put(y, w);
        if (!dic.containsKey(y)) dic.put(y, new HashMap<>());
        dic.get(y).put(x, w);
    }

    public Iterable<Integer> adj(int x) {
        if (!dic.containsKey(x)) return null;
        return dic.get(x).keySet();
    }

    public boolean isConnected(int x, int y) {
        return dic.get(x).containsKey(y);
    }

    public void removeEdge(int x, int y) {
        if (!isConnected(x, y))
            return;
        dic.get(x).remove(y);
        dic.get(y).remove(x);
    }

    public Iterable<Pair<Integer, Integer>> sortedEdges() {
        List<Pair<Integer, Integer>> ret = new ArrayList<>();
        for (int x: dic.keySet()) {
            for (int y: dic.get(x).keySet()) {
                if (y < x) continue;
                ret.add(new Pair<Integer,Integer>(x, y));
            }
        }
        ret.sort(cmp);
        return ret;
    }

    private void addMSTEdge(Pair<Integer, Integer> x, HashMap<Integer, HashMap<Integer, Double>> mst) {
        if (!mst.containsKey(x.getKey())) mst.put(x.getKey(), new HashMap<>());
        mst.get(x.getKey()).put(x.getValue(), dic.get(x.getKey()).get(x.getValue()));
        if (!mst.containsKey(x.getValue())) mst.put(x.getValue(), new HashMap<>());
        mst.get(x.getValue()).put(x.getKey(), dic.get(x.getValue()).get(x.getKey()));
    }

    public HashMap<Integer, HashMap<Integer, Double>> getMSTKruskal() {
        HashMap<Integer, HashMap<Integer, Double>> mst = new HashMap<>();
        HashMap<Integer, Integer> uf = new HashMap<>();
        for (int x: dic.keySet()) {
            uf.put(x, x);
        }
        for (Pair<Integer, Integer> x: sortedEdges()) {
            int root1 = x.getKey();
            while (root1 != uf.get(root1)) {
                uf.put(root1, uf.get(root1));
                root1 = uf.get(root1);
            }
            int root2 = x.getValue();
            while (root2 != uf.get(root2)) {
                uf.put(root2, uf.get(root2));
                root2 = uf.get(root2);
            }
            if (root1 != root2) {
                addMSTEdge(x, mst);
                uf.put(root1, root2);
            }
        }
        return mst;
    }

    public HashMap<Integer, HashMap<Integer, Double>> getMSTPrim() {
        HashMap<Integer, HashMap<Integer, Double>> mst = new HashMap<>();
        PriorityQueue<Pair<Integer, Integer>> h = new PriorityQueue<>(10, cmp);
        HashSet<Integer> vis = new HashSet<>();
        int x = dic.keySet().iterator().next();
        for (int y: dic.get(x).keySet()) {
            h.add(new Pair<>(x, y));
        }
        vis.add(x);
        while (!h.isEmpty()) {
            Pair<Integer, Integer> p = h.poll();
            if (vis.contains(p.getKey()) && vis.contains(p.getValue())) continue;
            addMSTEdge(p, mst);
            vis.add(p.getValue());
            for (int y: dic.get(p.getValue()).keySet()) {
                if (!vis.contains(y)) {
                    h.add(new Pair<Integer,Integer>(p.getValue(), y));
                }
            }
        }
        return mst;
    }

    public void printMST(String method) {
        HashMap<Integer, HashMap<Integer, Double>> mst;
        if (method.equals("Kruskal")) {
            mst = getMSTKruskal();
        } else {
            mst = getMSTPrim();
        }
        System.out.println("MST:");
        for (int x: mst.keySet()) {
            for (int y: mst.get(x).keySet()) {
                if (y < x) continue;
                System.out.println(x + " -> " + y);
            }
        }
        System.out.println("-----");
    }

    public static void main(String[] args) {
        MinSpanningTree g = new MinSpanningTree();
        try {
            BufferedReader in = new BufferedReader(new FileReader(".\\Week7_Graph\\test3.txt"));
            String line;
            String[] fields;
            while ((line = in.readLine()) != null) {
                fields = line.split(" ");
                if (fields[0].equals("add")) {
                    g.addEdge(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Double.parseDouble(fields[3]));
                } else if (fields[0].equals("remove")) {
                    g.removeEdge(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                } else if (fields[0].equals("mst")) {
                    g.printMST(fields[1]);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}