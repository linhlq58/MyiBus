package com.example.linhlee.myibus.objects;

/**
 * Created by Linh Lee on 4/13/2016.
 */
public class ReverseRouteItem {
    private String rrBusNumber;
    private int rrStationId;

    public ReverseRouteItem(String rrBusNumber, int rrStationId) {
        this.rrBusNumber = rrBusNumber;
        this.rrStationId = rrStationId;
    }

    public ReverseRouteItem() {
        super();
    }

    public String getRrBusNumber() {
        return rrBusNumber;
    }

    public void setRrBusNumber(String rrBusNumber) {
        this.rrBusNumber = rrBusNumber;
    }

    public int getRrStationId() {
        return rrStationId;
    }

    public void setRrStationId(int rrStationId) {
        this.rrStationId = rrStationId;
    }
}
