package com.example.linhlee.myibus.objects;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/11/2016.
 */
public class BusItem {
    private int busNumber;
    private String busName;

    private ArrayList<String> normalRoute;
    private ArrayList<String> reverseRoute;

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

    public ArrayList<String> getNormalRoute() {
        return normalRoute;
    }

    public void setNormalRoute(ArrayList<String> normalRoute) {
        this.normalRoute = normalRoute;
    }

    public ArrayList<String> getReverseRoute() {
        return reverseRoute;
    }

    public void setReverseRoute(ArrayList<String> reverseRoute) {
        this.reverseRoute = reverseRoute;
    }

}
