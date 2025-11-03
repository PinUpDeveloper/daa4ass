package org.example.scc;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    public static class Edge {
        public int to, weight;
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private final int n;
    private final List<List<Edge>> adj;

    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
    }

    public int size() {
        return n;
    }

    public List<List<Edge>> getAdj() {
        return adj;
    }
}
