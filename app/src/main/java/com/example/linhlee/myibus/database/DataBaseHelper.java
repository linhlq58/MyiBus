package com.example.linhlee.myibus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.linhlee.myibus.objects.BusItem;

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

        db.execSQL(CREATE_BUS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bus");

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

    public int updateBus(BusItem bus) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUS_NAME, bus.getBusName());

        int i = db.update(TABLE_BUS, values, KEY_BUS_NUMBER + " = ?",
                new String[] {String.valueOf(bus.getBusNumber())});

        db.close();

        return i;
    }

    public void deleteBus(BusItem bus) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_BUS, KEY_BUS_NUMBER + " = ?",
                new String[] {String.valueOf(bus.getBusNumber())});

        db.close();

        Log.d("deleteBus", bus.toString());

    }
}








