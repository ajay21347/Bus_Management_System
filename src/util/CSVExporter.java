package util;

import model.PathResult;
import java.io.FileWriter;
import java.util.List;

public class CSVExporter {

    public static void export(List<PathResult> paths) {
        try (FileWriter fw = new FileWriter("routes_output.csv")) {

            fw.write("Path,Time,Distance,Bus,Driver\n");

            for (PathResult p : paths) {
                fw.write(String.join(" -> ", p.getPath()) + ","
                        + TimeUtil.toHoursMinutes(p.getTotalTime()) + ","
                        + p.getTotalDistance() + ","
                        + p.getBus().getBusName() + ","
                        + p.getBus().getDriverName() + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
