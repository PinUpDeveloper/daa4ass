package org.example.util;

import java.util.HashMap;
import java.util.Map;

public class Metrics {
    private final Map<String, Long> counter = new HashMap<>();
    private final long startTime;

    public Metrics() {
        this.startTime = System.nanoTime();
    }

    public void inc(String key) {
        counter.put(key, counter.getOrDefault(key, 0L) + 1);
    }

    public void print() {
        System.out.println("--- Metrics ---");
        for (var e : counter.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
        long elapsed = System.nanoTime() - startTime;
        System.out.println("Total time: " + (elapsed / 1_000_000.0) + " ms");
    }
}
