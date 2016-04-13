package com.example.linhlee.myibus.objects;

/**
 * Created by Linh Lee on 4/13/2016.
 */
public class StationItem {
    private int stationId;
    private String stationName;
    private double stationLatitude;
    private double stationLongitude;

    public StationItem(int stationId, String stationName, double stationLatitude, double stationLongitude) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationLatitude = stationLatitude;
        this.stationLongitude = stationLongitude;
    }

    public StationItem() {
        super();
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getStationLatitude() {
        return stationLatitude;
    }

    public void setStationLatitude(double stationLatitude) {
        this.stationLatitude = stationLatitude;
    }

    public double getStationLongitude() {
        return stationLongitude;
    }

    public void setStationLongitude(double stationLongitude) {
        this.stationLongitude = stationLongitude;
    }
}
