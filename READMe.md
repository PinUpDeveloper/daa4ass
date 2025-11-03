# Smart City / Smart Campus Scheduling — SCC, Topological Sort & DAG Shortest Paths

## 📘 Project Overview
This project integrates and compares three key **graph algorithms** used in task scheduling and dependency resolution:
- **Tarjan’s Algorithm** for detecting Strongly Connected Components (SCC),
- **Topological Sort** for ordering acyclic components,
- **Shortest & Longest Path algorithms** for analyzing task dependencies within Directed Acyclic Graphs (DAGs).

The system models scheduling for a **Smart City / Smart Campus** — such as maintenance, cleaning, or repair tasks — with dependencies represented as a graph.

The project is developed in **Java 17** using **Maven** for build automation and **Jackson** for JSON data parsing.

---

## 📂 Project Structure
```
smartcity-scheduling/
│
├── pom.xml                            # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       ├── Main.java          # Entry point
│   │   │       ├── scc/
│   │   │       │   ├── Graph.java     # Graph structure
│   │   │       │   ├── TarjanSCC.java # SCC detection (Tarjan algorithm)
│   │   │       │   └── Condensation.java # SCC condensation to DAG
│   │   │       ├── topo/
│   │   │       │   └── TopologicalSort.java # DAG topological ordering
│   │   │       ├── dagsp/
│   │   │       │   └── DagShortestPaths.java # Shortest & longest path algorithms
│   │   │       ├── util/
│   │   │       │   ├── Metrics.java   # Performance tracking (time, operations)
│   │   │       │   └── DataLoader.java # JSON file handling
│   │   │       └── gen/
│   │   │           └── DatasetGenerator.java # Random dataset creation
│   │   └── resources/
│   │       ├── data/                  # Input datasets
│   │       │   ├── small1.json
│   │       │   ├── medium1.json
│   │       │   └── large1.json
│   │       └── output/                # Output results
│   │           └── results.json
│   └── test/java/org/example/
│       └── TestAlgorithms.java        # Unit tests
│
└── REPORT.md                          # Full analytical report
```

---

## ⚙️ How to Run

### 1. Clone or Extract the Project
```bash
git clone <repository-url>
cd smartcity-scheduling
```

### 2. Build with Maven
Make sure you have Maven and JDK 17+ installed.
```bash
mvn clean compile
```

### 3. Run the Program
```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

### 4. Run Tests
```bash
mvn test
```

### 5. Input and Output Files
- Input graphs: `src/main/resources/data/`
- Output results: `src/main/resources/output/results.json`

---

## 🧩 Example Input
```json
{
  "directed": true,
  "n": 6,
  "edges": [
    {"u": 0, "v": 1, "w": 4},
    {"u": 1, "v": 2, "w": 3},
    {"u": 2, "v": 0, "w": 2},
    {"u": 2, "v": 3, "w": 6},
    {"u": 3, "v": 4, "w": 5},
    {"u": 4, "v": 5, "w": 1}
  ],
  "source": 2
}
```

---

## 📊 Example Output
```json
{
  "graphId": 1,
  "sccCount": 2,
  "topoOrder": [0, 1],
  "shortestPaths": [0, 4, 6, 8],
  "longestPaths": [0, 6, 9, 13],
  "executionTimeMs": 14
}
```

---

## 🧠 Algorithm Summary

| Algorithm | Purpose | Complexity | Notes |
|------------|----------|-------------|--------|
| **TarjanSCC** | Find strongly connected components | O(V + E) | DFS-based |
| **Condensation Graph** | Compress SCCs into DAG | O(V + E) | Reduces cyclic graph |
| **Topological Sort** | Order nodes in DAG | O(V + E) | Kahn/DFS |
| **DAG Shortest Path** | Compute min path | O(V + E) | DP over topo order |
| **DAG Longest Path** | Compute max path | O(V + E) | Detects critical path |

---

## 📈 Sample Console Output
```
=== SCC Detection ===
SCCs found: [ [0, 1, 2], [3], [4, 5] ]

=== Condensation DAG ===
Edges: (SCC0 -> SCC1), (SCC1 -> SCC2)

=== Topological Sort ===
Order: [0, 1, 2]

=== DAG Shortest Path ===
Source: 0
Distances: [0, 4, 9, 13, 14]

=== DAG Longest Path ===
Critical Path: [0 -> 2 -> 3 -> 4 -> 5]
Length: 14

Execution Time: 12.3 ms
```

---

## 🔬 Performance & Metrics Example
```
Graph size: 20 nodes, 40 edges
Tarjan DFS calls: 20
SCCs found: 4
Topo Sort edges processed: 16
Shortest Path relaxations: 38
Total time: 18.5 ms
```

---

## 🏁 Conclusion
This project demonstrates the **integration of SCC detection, DAG condensation, and path algorithms** in a unified scheduling system.

- **SCC compression** simplifies cyclic dependency graphs.
- **Topological sorting** ensures correct task ordering.
- **Shortest and longest path analysis** identifies optimal and critical routes for smart city operations.

These combined techniques provide an efficient model for **dependency scheduling, resource allocation**, and **cycle detection** in complex networks.

---

## 👨‍💻 Author
**Tazhibayev Marsel**  
Course: **Design and Analysis of Algorithms (DAA)**  
Project: **Smart City Scheduling (Assignment 4)**  
Year: **2025**
