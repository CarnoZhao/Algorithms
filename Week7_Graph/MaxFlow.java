import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaxFlow extends WeightedDigraph {
    public Double findMaxFlow(int a, int b) {
        HashMap<Integer, HashMap<Integer, Double>> residual = new HashMap<>();
        for (int x: dic.keySet()) {
            if (!residual.containsKey(x)) residual.put(x, new HashMap<>());
            for (int y: adj(x)) {
                if (!residual.containsKey(y)) residual.put(y, new HashMap<>());
                residual.get(x).put(y, dic.get(x).get(y));
                residual.get(y).put(x, 0.);
            }
        }
        List<Integer> path = new ArrayList<>();
        double ret = 0;
        double tmp;
        while ((path = augFlow(a, b, residual)) != null) {
            int n = path.size();
            double mi = Double.MAX_VALUE;
            for (int i = 0; i < n - 1; i ++) {
                mi = Math.min(residual.get(path.get(i + 1)).get(path.get(i)), mi);
            }
            for (int i = 0; i < n - 1; i ++) {
                tmp = residual.get(path.get(i + 1)).get(path.get(i));
                residual.get(path.get(i + 1)).put(path.get(i), tmp - mi);
                tmp = residual.get(path.get(i)).get(path.get(i + 1));
                residual.get(path.get(i)).put(path.get(i + 1), tmp + mi);
            }
            ret += mi;
        }
        return ret;
    }

    private List<Integer> augFlow(int a, int b, HashMap<Integer, HashMap<Integer, Double>> residual) {
        HashMap<Integer, Integer> vis = new HashMap<>();
        Queue<Integer> q = new LinkedList<>();
        q.add(a);
        vis.put(a, null);
        while (!q.isEmpty()) {
            a = q.poll();
            if (a == b) break;
            for (int c: adj(a)) {
                if (!vis.containsKey(c) && residual.get(a).get(c) > 0) {
                    q.add(c);
                    vis.put(c, a);
                }
            }
        }
        List<Integer> ret = new ArrayList<>();
        ret.add(b);
        while (vis.get(b) != null) {
            b = vis.get(b);
            ret.add(b);
        }
        return ret.size() == 1 ? null : ret;
    }

    public static void main(String[] args) {
        MaxFlow g = new MaxFlow();
        try {
            BufferedReader in = new BufferedReader(new FileReader(".\\Week7_Graph\\test5.txt"));
            String line;
            String[] fields;
            while ((line = in.readLine()) != null) {
                fields = line.split(" ");
                if (fields[0].equals("add")) {
                    g.addEdge(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Double.parseDouble(fields[3]));
                } else if (fields[0].equals("remove")) {
                    g.removeEdge(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                } else if (fields[0].equals("maxflow")) {
                    double ret = g.findMaxFlow(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                    System.out.println(ret);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}