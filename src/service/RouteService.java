package service;

import java.io.*;
import java.util.*;
import model.*;

public class RouteService {

    private Map<Integer, String> stops = new HashMap<>();
    private Map<Integer, List<int[]>> graph = new HashMap<>();

    // 🔹 Sample buses & drivers (Feature 5)
    private final String[] buses = {
        "UK Roadways", "Volvo Deluxe", "Hills Express",
        "City Rider", "Mountain King"
    };

    private final String[] drivers = {
        "Ramesh", "Anil", "Suresh", "Mahesh", "Vikas"
    };

    public RouteService() {
        loadStops();
        loadTransfers();
    }

    // ---------------- LOAD STOPS ----------------
    private void loadStops() {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("src/data/stops.txt"))) {

            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                stops.put(Integer.parseInt(p[0]), p[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- LOAD TRANSFERS ----------------
    private void loadTransfers() {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("src/data/transfers.txt"))) {

            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                int from = Integer.parseInt(p[0]);
                int to = Integer.parseInt(p[1]);
                int time = Integer.parseInt(p[3]) / 60; // sec → min

                graph.computeIfAbsent(from, k -> new ArrayList<>())
                     .add(new int[]{to, time});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================
    // FEATURE 6: FILTERED ROUTE SEARCH
    // =====================================================
    public List<PathResult> findRoutes(int src, int dest, String filter) {

        List<PathResult> results = new ArrayList<>();
        dfs(src, dest, new HashSet<>(), new ArrayList<>(), 0, results);

        // Sort by total time
        results.sort(Comparator.comparingInt(PathResult::getTotalTime));

        if (results.isEmpty()) return results;

        switch (filter) {
            case "FASTEST":
                return List.of(results.get(0));

            case "LEAST_STOPS":
                results.sort(Comparator.comparingInt(r -> r.getPath().size()));
                return List.of(results.get(0));

            default: // "ALL"
                return results;
        }
    }

    // =====================================================
    // DFS TO FIND ALL PATHS
    // =====================================================
    private void dfs(int curr, int dest,
                     Set<Integer> visited,
                     List<Integer> path,
                     int time,
                     List<PathResult> results) {

        visited.add(curr);
        path.add(curr);

        if (curr == dest) {

            // Convert stop IDs → stop names
            List<String> pathNames = new ArrayList<>();
            for (int id : path) {
                pathNames.add(stops.get(id));
            }

            // 🔹 Assign bus & driver (Feature 5)
            int index = results.size() % buses.length;
            Bus bus = new Bus(buses[index], drivers[index]);

            // distance = demo calculation (2 km per min)
            int distance = time * 2;

            results.add(new PathResult(pathNames, time, distance, bus));
        }

        for (int[] edge : graph.getOrDefault(curr, new ArrayList<>())) {
            if (!visited.contains(edge[0])) {
                dfs(edge[0], dest,
                    visited, path,
                    time + edge[1],
                    results);
            }
        }

        visited.remove(curr);
        path.remove(path.size() - 1);
    }
}
