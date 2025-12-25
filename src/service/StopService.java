package service;

import algorithm.TernarySearchTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StopService {

    private Map<String, Integer> nameToId = new HashMap<>();
    private TernarySearchTree tst = new TernarySearchTree();

    public StopService() {
        loadStops();
    }

    private void loadStops() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/data/stops.txt"))) {

            br.readLine(); // header
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int id = Integer.parseInt(p[0]);
                String name = p[1];

                nameToId.put(name.toLowerCase(), id);
                tst.insert(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStopId(String stopName) {
        return nameToId.getOrDefault(stopName.toLowerCase(), -1);
    }

    public List<String> suggestStops(String prefix) {
        return tst.autoSuggest(prefix);
    }
}
