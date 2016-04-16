package com.example.linhlee.myibus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.linhlee.myibus.objects.BusItem;
import com.example.linhlee.myibus.objects.NormalRouteItem;
import com.example.linhlee.myibus.objects.ReverseRouteItem;
import com.example.linhlee.myibus.objects.StationItem;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/11/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "iBusDB";

    private static final String TABLE_BUS = "bus";
    private static final String KEY_BUS_NUMBER = "bus_number";
    private static final String KEY_BUS_NAME = "bus_name";
    private static final String[] BUS_COLUMNS = {KEY_BUS_NUMBER, KEY_BUS_NAME};

    private static final String TABLE_STATION = "station";
    private static final String KEY_STATION_ID = "station_id";
    private static final String KEY_STATION_NAME = "station_name";
    private static final String KEY_STATION_LATITUDE = "station_latitude";
    private static final String KEY_STATION_LONGITUDE = "station_longitude";
    private static final String[] STATION_COLUMNS = {KEY_STATION_ID, KEY_STATION_NAME, KEY_STATION_LATITUDE, KEY_STATION_LONGITUDE};

    private static final String TABLE_NORMALROUTE = "normal_route";
    private static final String KEY_NR_BUS_NUMBER = "nr_bus_number";
    private static final String KEY_NR_STATION_ID = "nr_station_id";

    private static final String TABLE_REVERSEROUTE = "reverse_route";
    private static final String KEY_RR_BUS_NUMBER = "rr_bus_number";
    private static final String KEY_RR_STATION_ID = "rr_station_id";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BUS_TABLE = "CREATE TABLE IF NOT EXISTS bus ("
                + "bus_number INTEGER PRIMARY KEY, "
                + "bus_name TEXT)";

        String CREATE_STATION_TABLE = "CREATE TABLE IF NOT EXISTS station ("
                + "station_id INTEGER PRIMARY KEY, "
                + "station_name TEXT, "
                + "station_latitude DOUBLE, "
                + "station_longitude DOUBLE)";

        String CREATE_NORMALROUTE_TABLE = "CREATE TABLE IF NOT EXISTS normal_route ("
                + "nr_id INTEGER PRIMARY KEY, "
                + "nr_bus_number INTEGER, "
                + "nr_station_id INTEGER)";

        String CREATE_REVERSEROUTE_TABLE = "CREATE TABLE IF NOT EXISTS reverse_route ("
                + "rr_id INTEGER PRIMARY KEY, "
                + "rr_bus_number INTEGER, "
                + "rr_station_id INTEGER)";

        db.execSQL(CREATE_BUS_TABLE);
        db.execSQL(CREATE_STATION_TABLE);
        db.execSQL(CREATE_NORMALROUTE_TABLE);
        db.execSQL(CREATE_REVERSEROUTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bus");
        db.execSQL("DROP TABLE IF EXISTS station");
        db.execSQL("DROP TABLE IF EXISTS normal_route");
        db.execSQL("DROP TABLE IF EXISTS reverse_route");

        this.onCreate(db);
    }

    public void addBus(BusItem bus) {

        Log.d("addBus", bus.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUS_NUMBER, bus.getBusNumber());
        values.put(KEY_BUS_NAME, bus.getBusName());

        db.insert(TABLE_BUS, null, values);

        db.close();
    }

    public void addStation(StationItem station) {
        Log.d("addStation", station.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATION_ID, station.getStationId());
        values.put(KEY_STATION_NAME, station.getStationName());
        values.put(KEY_STATION_LATITUDE, station.getStationLatitude());
        values.put(KEY_STATION_LONGITUDE, station.getStationLongitude());

        db.insert(TABLE_STATION, null, values);

        db.close();
    }

    public void addNormalRoute(NormalRouteItem normalRoute) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NR_BUS_NUMBER, normalRoute.getNrBusNumber());
        values.put(KEY_NR_STATION_ID, normalRoute.getNrStationId());

        db.insert(TABLE_NORMALROUTE, null, values);

        db.close();
    }

    public void addReverseRoute(ReverseRouteItem reverseRoute) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RR_BUS_NUMBER, reverseRoute.getRrBusNumber());
        values.put(KEY_RR_STATION_ID, reverseRoute.getRrStationId());

        db.insert(TABLE_REVERSEROUTE, null, values);

        db.close();
    }

    public BusItem getBus(int number) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BUS, BUS_COLUMNS, "bus_number = ?",
                new String[]{String.valueOf(number)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        BusItem bus = new BusItem();
        bus.setBusNumber(cursor.getInt(0));
        bus.setBusName(cursor.getString(1));

        Log.d("getBus(" + number + ")", bus.toString());

        return bus;
    }

    public StationItem getStation(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STATION, STATION_COLUMNS, "station_id = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        StationItem station = new StationItem();
        station.setStationId(cursor.getInt(0));
        station.setStationName(cursor.getString(1));
        station.setStationLatitude(cursor.getDouble(2));
        station.setStationLongitude(cursor.getDouble(3));

        return station;
    }

    public ArrayList<BusItem> getAllBus() {
        ArrayList<BusItem> listBus = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_BUS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        BusItem bus = null;
        if (cursor.moveToFirst()) {
            do {
                bus = new BusItem();
                bus.setBusNumber(cursor.getInt(0));
                bus.setBusName(cursor.getString(1));

                listBus.add(bus);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBus()", listBus.toString());

        return listBus;
    }

    public ArrayList<String> getNormalRouteStationByBusNumber(int number) {
        ArrayList<String> listNormalRouteStation = new ArrayList<>();

        String query = "SELECT s.station_name FROM normal_route nr JOIN station s "
                + "ON nr.nr_station_id = s.station_id "
                + "WHERE nr.nr_bus_number = " + number + " ORDER BY nr.nr_id ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                listNormalRouteStation.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        Log.d("getNormalRoute", "success");

        return listNormalRouteStation;
    }

    public ArrayList<String> getReverseRouteStationByBusNumber(int number) {
        ArrayList<String> listReverseRouteStation = new ArrayList<>();

        String query = "SELECT s.station_name FROM reverse_route rr JOIN station s "
                + "ON rr.rr_station_id = s.station_id "
                + "WHERE rr.rr_bus_number = " + number + " ORDER BY rr.rr_id ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                listReverseRouteStation.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        Log.d("getReverseRoute", "success");

        return listReverseRouteStation;
    }

    public int updateBus(BusItem bus) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUS_NAME, bus.getBusName());

        int i = db.update(TABLE_BUS, values, KEY_BUS_NUMBER + " = ?",
                new String[]{String.valueOf(bus.getBusNumber())});

        db.close();

        return i;
    }

    public int updateStation(StationItem station) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATION_NAME, station.getStationName());
        values.put(KEY_STATION_LATITUDE, station.getStationLatitude());
        values.put(KEY_STATION_LONGITUDE, station.getStationLongitude());

        int i = db.update(TABLE_STATION, values, KEY_STATION_ID + " = ?",
                new String[] {String.valueOf(station.getStationId())});

        db.close();

        return i;
    }

    public int updateNormalRoute(NormalRouteItem normalRoute) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NR_BUS_NUMBER, normalRoute.getNrBusNumber());
        values.put(KEY_NR_STATION_ID, normalRoute.getNrStationId());

        int i = db.update(TABLE_NORMALROUTE, values, KEY_NR_BUS_NUMBER + " = ?",
                new String[] {String.valueOf(normalRoute.getNrBusNumber())});

        db.close();

        return i;
    }

    public int updateReverseRoute(ReverseRouteItem reverseRoute) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RR_BUS_NUMBER, reverseRoute.getRrBusNumber());
        values.put(KEY_RR_STATION_ID, reverseRoute.getRrStationId());

        int i = db.update(TABLE_REVERSEROUTE, values, KEY_RR_BUS_NUMBER + " = ?",
                new String[] {String.valueOf(reverseRoute.getRrBusNumber())});

        db.close();

        return i;
    }

    public void deleteBus(BusItem bus) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_BUS, KEY_BUS_NUMBER + " = ?",
                new String[]{String.valueOf(bus.getBusNumber())});

        db.close();

        Log.d("deleteBus", bus.toString());

    }

    public void deleteBusByBusNumber(int number) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_BUS, KEY_BUS_NUMBER + " = ?",
                new String[]{String.valueOf(number)});

        db.close();
    }

    public void deleteStation(StationItem station) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_STATION, KEY_STATION_ID + " = ?",
                new String[]{String.valueOf(station.getStationId())});

        db.close();
    }

    public void deleteStationById(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_STATION, KEY_STATION_ID + " = ?",
                new String[] {String.valueOf(id)});

        db.close();
    }

    public void deleteNormalRoute(NormalRouteItem normalRoute) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NORMALROUTE, KEY_NR_BUS_NUMBER + " = ?",
                new String[] {String.valueOf(normalRoute.getNrBusNumber())});

        db.close();
    }

    public void deleteNormalRouteByBusNumber(int number) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NORMALROUTE, KEY_NR_BUS_NUMBER + " = ?",
                new String[] {String.valueOf(number)});

        db.close();
    }

    public void deleteReverseRoute(ReverseRouteItem reverseRoute) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_REVERSEROUTE, KEY_RR_BUS_NUMBER + " = ?",
                new String[] {String.valueOf(reverseRoute.getRrBusNumber())});

        db.close();
    }

    public void deleteReverseRouteByBusNumber(int number) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_REVERSEROUTE, KEY_RR_BUS_NUMBER + " = ?",
                new String[] {String.valueOf(number)});

        db.close();
    }

}








