package model;

public class Bus {
    private String busName;
    private String driverName;

    public Bus(String busName, String driverName) {
        this.busName = busName;
        this.driverName = driverName;
    }

    public String getBusName() {
        return busName;
    }

    public String getDriverName() {
        return driverName;
    }
}
