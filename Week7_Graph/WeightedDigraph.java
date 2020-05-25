import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class WeightedDigraph {
    HashMap<Integer, HashMap<Integer, Double>> dic = new HashMap<>();

    void addEdge(int x, int y, double w) {
        if (!dic.containsKey(x)) dic.put(x, new HashMap<>());
        dic.get(x).put(y, w);
    }

    public Iterable<Integer> adj(int x) {
        if (!dic.containsKey(x)) return new HashSet<>();
        return dic.get(x).keySet();
    }

    public boolean isConnected(int x, int y) {
        return dic.get(x).containsKey(y);
    }

    public void removeEdge(int x, int y) {
        if (!isConnected(x, y))
            return;
        dic.get(x).remove(y);
    }

    public double shortestPath(int x, int y) {
        HashMap<Integer, Double> dis = new HashMap<>();
        PriorityQueue<Integer> h = new PriorityQueue<>(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {return dis.get(a).compareTo(dis.get(b));}
        });
        dis.put(x, 0.0); h.add(x);
        while (!h.isEmpty()) {
            x = h.poll();
            for (int nex: adj(x)) {
                if (!dis.containsKey(nex)) {
                    dis.put(nex, dis.get(x) + dic.get(x).get(nex));
                    h.add(nex);
                } else if (dis.get(nex) > dis.get(x) + dic.get(x).get(nex)) {
                    dis.put(nex, dis.get(x) + dic.get(x).get(nex));
                }
            }
        }
        return dis.get(y);
    }


    public static void main(String[] args) {
        WeightedDigraph g = new WeightedDigraph();
        try {
            BufferedReader in = new BufferedReader(new FileReader(".\\Week7_Graph\\test4.txt"));
            String line;
            String[] fields;
            while ((line = in.readLine()) != null) {
                fields = line.split(" ");
                if (fields[0].equals("add")) {
                    g.addEdge(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Double.parseDouble(fields[3]));
                } else if (fields[0].equals("remove")) {
                    g.removeEdge(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                } else if (fields[0].equals("shortest")) {
                    double ret = g.shortestPath(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                    System.out.println(ret);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}