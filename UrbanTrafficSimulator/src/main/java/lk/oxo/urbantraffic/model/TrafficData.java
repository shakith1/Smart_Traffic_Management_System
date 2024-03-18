package lk.oxo.urbantraffic.model;

public class TrafficData {
    private long vehicleSpeed;
    private TrafficLightStatus lightStatus;
    private double latitude;
    private double longitude;
    private TrafficZone trafficZone;

    @Override
    public String toString() {
        return "TrafficData{" +
                "vehicleSpeed=" + vehicleSpeed +
                ", lightStatus=" + lightStatus +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", trafficZone=" + trafficZone +
                '}';
    }

    public TrafficData(long vehicleSpeed, TrafficLightStatus lightStatus, double latitude, double longitude, TrafficZone trafficZone) {
        this.vehicleSpeed = vehicleSpeed;
        this.lightStatus = lightStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trafficZone = trafficZone;
    }

    public double getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(long vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public TrafficLightStatus getLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(TrafficLightStatus lightStatus) {
        this.lightStatus = lightStatus;
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

    public TrafficZone getTrafficZone() {
        return trafficZone;
    }

    public void setTrafficZone(TrafficZone trafficZone) {
        this.trafficZone = trafficZone;
    }
}
