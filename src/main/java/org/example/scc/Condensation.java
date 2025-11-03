package org.example.scc;

import java.util.List;

public class Condensation {
    private final Graph original;
    private final List<List<Integer>> components;

    public Condensation(Graph g, List<List<Integer>> comps) {
        this.original = g;
        this.components = comps;
    }

    public Graph buildDAG() {
        int compCount = components.size();
        Graph dag = new Graph(compCount);
        int[] compIndex = new int[original.size()];

        for (int i = 0; i < compCount; i++)
            for (int v : components.get(i))
                compIndex[v] = i;

        for (int u = 0; u < original.size(); u++) {
            for (Graph.Edge e : original.getAdj().get(u)) {
                int cu = compIndex[u];
                int cv = compIndex[e.to];
                if (cu != cv) dag.addEdge(cu, cv, e.weight);
            }
        }
        return dag;
    }
}
