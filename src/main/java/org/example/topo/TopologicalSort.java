package org.example.topo;

import org.example.scc.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {
    public static List<Integer> sort(Graph g) {
        int n = g.size();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) {
            for (Graph.Edge e : g.getAdj().get(u)) {
                indeg[e.to]++;
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (Graph.Edge e : g.getAdj().get(u)) {
                indeg[e.to]--;
                if (indeg[e.to] == 0) q.add(e.to);
            }
        }

        return order;
    }
}
