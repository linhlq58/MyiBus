package com.example.linhlee.myibus.objects;

/**
 * Created by Linh Lee on 4/13/2016.
 */
public class ReverseRouteItem {
    private int rrBusNumber;
    private int rrStationId;

    public ReverseRouteItem(int rrBusNumber, int rrStationId) {
        this.rrBusNumber = rrBusNumber;
        this.rrStationId = rrStationId;
    }

    public ReverseRouteItem() {
        super();
    }

    public int getRrBusNumber() {
        return rrBusNumber;
    }

    public void setRrBusNumber(int rrBusNumber) {
        this.rrBusNumber = rrBusNumber;
    }

    public int getRrStationId() {
        return rrStationId;
    }

    public void setRrStationId(int rrStationId) {
        this.rrStationId = rrStationId;
    }
}
