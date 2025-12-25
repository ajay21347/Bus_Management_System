package algorithm;

import java.util.*;

public class Graph {
    private Map<Integer, List<Edge>> adjList = new HashMap<>();

    public void addEdge(int from, int to, int weight) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>())
               .add(new Edge(to, weight));
    }

    public Map<Integer, List<Edge>> getAdjList() {
        return adjList;
    }
}
