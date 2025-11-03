package org.example.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.scc.Graph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DatasetGenerator {

    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException {
        generateAllDatasets("data/");
    }

    public static void generateAllDatasets(String folder) throws IOException {
        generateCategory(folder, "small", 6, 10, 3);
        generateCategory(folder, "medium", 10, 20, 3);
        generateCategory(folder, "large", 20, 50, 3);
    }

    private static void generateCategory(String folder, String name, int minN, int maxN, int count) throws IOException {
        for (int i = 1; i <= count; i++) {
            int n = rand.nextInt(maxN - minN + 1) + minN;
            boolean cyclic = rand.nextBoolean();
            Graph g = generateGraph(n, cyclic);

            Map<String, Object> json = new LinkedHashMap<>();
            json.put("directed", true);
            json.put("n", n);
            json.put("edges", buildEdgeList(g));
            json.put("source", rand.nextInt(n));
            json.put("weight_model", "edge");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(folder + name + "-" + i + ".json")) {
                gson.toJson(json, writer);
            }
        }
        System.out.println("Generated " + count + " " + name + " datasets.");
    }

    private static Graph generateGraph(int n, boolean cyclic) {
        Graph g = new Graph(n);
        int edgeCount = rand.nextInt(n * 2) + n; // от n до 3n рёбер

        for (int i = 0; i < edgeCount; i++) {
            int u = rand.nextInt(n);
            int v = rand.nextInt(n);
            if (!cyclic && v <= u) continue; // упрощённо: делаем DAG
            if (u == v) continue; // без петель
            int w = rand.nextInt(9) + 1;
            g.addEdge(u, v, w);
        }
        return g;
    }

    private static List<Map<String, Object>> buildEdgeList(Graph g) {
        List<Map<String, Object>> edges = new ArrayList<>();
        for (int u = 0; u < g.size(); u++) {
            for (Graph.Edge e : g.getAdj().get(u)) {
                Map<String, Object> edge = new LinkedHashMap<>();
                edge.put("u", u);
                edge.put("v", e.to);
                edge.put("w", e.weight);
                edges.add(edge);
            }
        }
        return edges;
    }
}
