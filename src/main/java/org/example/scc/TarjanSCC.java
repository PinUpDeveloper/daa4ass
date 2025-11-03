package org.example.scc;

import java.util.*;

public class TarjanSCC {
    private final Graph graph;
    private final List<List<Integer>> components = new ArrayList<>();
    private int time = 0;
    private int[] low, disc;
    private boolean[] inStack;
    private Deque<Integer> stack = new ArrayDeque<>();

    public TarjanSCC(Graph graph) {
        this.graph = graph;
        int n = graph.size();
        low = new int[n];
        disc = new int[n];
        inStack = new boolean[n];
        Arrays.fill(disc, -1);
        for (int i = 0; i < n; i++) {
            if (disc[i] == -1) dfs(i);
        }
    }

    private void dfs(int u) {
        disc[u] = low[u] = ++time;
        stack.push(u);
        inStack[u] = true;

        for (Graph.Edge e : graph.getAdj().get(u)) {
            int v = e.to;
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> comp = new ArrayList<>();
            int v;
            do {
                v = stack.pop();
                inStack[v] = false;
                comp.add(v);
            } while (v != u);
            components.add(comp);
        }
    }

    public List<List<Integer>> getComponents() {
        return components;
    }
}
