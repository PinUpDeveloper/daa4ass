package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.scc.*;
import org.example.topo.TopologicalSort;
import org.example.dagsp.DagShortestPaths;
import org.example.util.Metrics;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = loadGraph("data/tasks.json");
            System.out.println("Loaded " + graph.size() + " nodes.");

            TarjanSCC scc = new TarjanSCC(graph);
            List<List<Integer>> comps = scc.getComponents();
            System.out.println("\nSCCs: " + comps);

            Condensation condensation = new Condensation(graph, comps);
            Graph dag = condensation.buildDAG();

            List<Integer> topo = TopologicalSort.sort(dag);
            System.out.println("\nTopo order: " + topo);

            Metrics metrics = new Metrics();
            DagShortestPaths dsp = new DagShortestPaths(dag, metrics);
            dsp.compute(0);
            System.out.println("\nShortest: " + Arrays.toString(dsp.getShortest()));
            System.out.println("Longest: " + Arrays.toString(dsp.getLongest()));
            metrics.print();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Graph loadGraph(String filePath) throws Exception {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> data = gson.fromJson(new FileReader(filePath), type);
        int n = ((Double) data.get("n")).intValue();
        Graph g = new Graph(n);
        List<Map<String, Object>> edges = (List<Map<String, Object>>) data.get("edges");
        for (Map<String, Object> e : edges) {
            int u = ((Double) e.get("u")).intValue();
            int v = ((Double) e.get("v")).intValue();
            int w = ((Double) e.get("w")).intValue();
            g.addEdge(u, v, w);
        }
        return g;
    }
}
