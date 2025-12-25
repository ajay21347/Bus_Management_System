package algorithm;

import java.util.*;

public class Dijkstra {

    public static List<Integer> shortestPath(Graph graph, int src, int dest) {

        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        PriorityQueue<int[]> pq =
            new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        pq.add(new int[]{src, 0});
        dist.put(src, 0);
        parent.put(src, -1);

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];

            if (node == dest) break;

            for (Edge e : graph.getAdjList().getOrDefault(node, new ArrayList<>())) {
                int newDist = dist.get(node) + e.weight;

                if (!dist.containsKey(e.to) || newDist < dist.get(e.to)) {
                    dist.put(e.to, newDist);
                    parent.put(e.to, node);
                    pq.add(new int[]{e.to, newDist});
                }
            }
        }

        // 🔁 BUILD PATH
        List<Integer> path = new ArrayList<>();
        int curr = dest;

        while (curr != -1) {
            path.add(curr);
            curr = parent.getOrDefault(curr, -1);
        }

        Collections.reverse(path);
        return path;
    }
}
