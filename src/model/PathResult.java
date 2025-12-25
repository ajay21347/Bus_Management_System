package model;

import java.util.List;

public class PathResult {

    private List<String> path;
    private int totalTime;
    private int totalDistance;
    private Bus bus;

    public PathResult(List<String> path, int totalTime, int totalDistance, Bus bus) {
        this.path = path;
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.bus = bus;
    }

    public List<String> getPath() {
        return path;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public Bus getBus() {
        return bus;
    }
}
