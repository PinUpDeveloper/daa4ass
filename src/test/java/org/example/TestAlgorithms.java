package org.example;

import org.example.dagsp.DagShortestPaths;
import org.example.scc.Condensation;
import org.example.scc.Graph;
import org.example.scc.TarjanSCC;
import org.example.topo.TopologicalSort;
import org.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestAlgorithms {

    @Test
    void testSCCDetection() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1); // cycle 0–1–2
        g.addEdge(3, 4, 1);

        TarjanSCC scc = new TarjanSCC(g);
        List<List<Integer>> comps = scc.getComponents();

        // Expect at least one component with {0,1,2}
        boolean hasCycle = comps.stream().anyMatch(c -> c.containsAll(List.of(0, 1, 2)));
        assertTrue(hasCycle, "SCC {0,1,2} should exist");
    }

    @Test
    void testTopologicalSort() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(0, 3, 1);

        List<Integer> order = TopologicalSort.sort(g);
        // 0 must come before 1 and 3
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(3));
    }

    @Test
    void testDAGShortestAndLongest() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(0, 3, 4);

        Metrics metrics = new Metrics();
        DagShortestPaths dsp = new DagShortestPaths(g, metrics);
        dsp.compute(0);

        double[] shortest = dsp.getShortest();
        double[] longest = dsp.getLongest();

        assertEquals(0.0, shortest[0]);
        assertEquals(1.0, shortest[1]);
        assertEquals(3.0, shortest[2]); // 0→1→2
        assertEquals(4.0, shortest[3]); // 0→3

        assertTrue(longest[2] > longest[1]); // longest path grows
    }

    @Test
    void testCondensationGraph() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 0, 1); // SCC 0–1
        g.addEdge(2, 3, 1);

        TarjanSCC scc = new TarjanSCC(g);
        Condensation c = new Condensation(g, scc.getComponents());
        Graph dag = c.buildDAG();

        assertNotNull(dag);
        assertTrue(dag.size() <= g.size());
    }
}
