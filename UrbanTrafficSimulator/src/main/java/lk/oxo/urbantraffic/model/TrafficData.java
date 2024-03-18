package lk.oxo.urbantraffic.model;

public class TrafficData {
    private double vehicleSpeed;
    private TrafficLightStats lightStats;
    private double latitude;
    private double longitude;

    public double getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(double vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public TrafficLightStats getLightStats() {
        return lightStats;
    }

    public void setLightStats(TrafficLightStats lightStats) {
        this.lightStats = lightStats;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
