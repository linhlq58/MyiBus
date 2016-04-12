package com.example.linhlee.myibus.objects;

/**
 * Created by Linh Lee on 4/11/2016.
 */
public class BusItem {
    private int busNumber;
    private String busName;

    public BusItem(int busNumber, String busName) {
        this.busNumber = busNumber;
        this.busName = busName;
    }

    public BusItem() {
        super();
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }
}
