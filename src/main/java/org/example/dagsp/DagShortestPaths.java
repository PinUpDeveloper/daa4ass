package org.example.dagsp;

import org.example.scc.Graph;
import org.example.topo.TopologicalSort;
import org.example.util.Metrics;

import java.util.*;

public class DagShortestPaths {
    private final Graph g;
    private final Metrics metrics;
    private double[] dist;
    private double[] longest;

    public DagShortestPaths(Graph g, Metrics metrics) {
        this.g = g;
        this.metrics = metrics;
    }

    public void compute(int source) {
        int n = g.size();
        dist = new double[n];
        longest = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(longest, Double.NEGATIVE_INFINITY);
        dist[source] = 0;
        longest[source] = 0;

        List<Integer> topo = TopologicalSort.sort(g);
        for (int u : topo) {
            for (Graph.Edge e : g.getAdj().get(u)) {
                metrics.inc("relax");
                if (dist[u] + e.weight < dist[e.to])
                    dist[e.to] = dist[u] + e.weight;
                if (longest[u] + e.weight > longest[e.to])
                    longest[e.to] = longest[u] + e.weight;
            }
        }
    }

    public double[] getShortest() {
        return dist;
    }

    public double[] getLongest() {
        return longest;
    }
}
