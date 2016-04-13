package com.example.linhlee.myibus.objects;

/**
 * Created by Linh Lee on 4/13/2016.
 */
public class NormalRouteItem {
    private int nrBusNumber;
    private int nrStationId;

    public NormalRouteItem(int nrBusNumber, int nrStationId) {
        this.nrBusNumber = nrBusNumber;
        this.nrStationId = nrStationId;
    }

    public NormalRouteItem() {
        super();
    }

    public int getNrBusNumber() {
        return nrBusNumber;
    }

    public void setNrBusNumber(int nrBusNumber) {
        this.nrBusNumber = nrBusNumber;
    }

    public int getNrStationId() {
        return nrStationId;
    }

    public void setNrStationId(int nrStationId) {
        this.nrStationId = nrStationId;
    }
}
