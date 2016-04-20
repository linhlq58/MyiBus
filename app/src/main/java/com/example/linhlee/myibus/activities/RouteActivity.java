package com.example.linhlee.myibus.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linhlee.myibus.R;
import com.example.linhlee.myibus.adapters.ListStationAdapter;
import com.example.linhlee.myibus.database.DataBaseHelper;
import com.example.linhlee.myibus.utils.GMapV2Direction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Lee on 4/13/2016.
 */
public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback{

    private String titleName;
    private ArrayList<String> listNormalRoute;
    private ArrayList<String> listReverseRoute;

    private TextView title;
    private ImageView backButton;
    private Button redirectButton;
    private Button deleteButton;

    private ListView listStation;
    private ListStationAdapter listStationAdapter;

    private boolean normalDirect = true;
    private String stationSelected = null;

    private GoogleMap myMap;
    private GMapV2Direction md;

    private DataBaseHelper db = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_route);
        mapFragment.getMapAsync(this);

        title = (TextView) findViewById(R.id.text_title);
        backButton = (ImageView) findViewById(R.id.btn_back);
        redirectButton = (Button) findViewById(R.id.btn_redirect);
        deleteButton = (Button) findViewById(R.id.btn_delete);
        listStation = (ListView) findViewById(R.id.list_station);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titleName = extras.getString("title");
            listNormalRoute = extras.getStringArrayList("listNormalRoute");
            listReverseRoute = extras.getStringArrayList("listReverseRoute");
        }
        title.setText(titleName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listStationAdapter = new ListStationAdapter(this, R.layout.list_station_item, listNormalRoute);
        listStation.setAdapter(listStationAdapter);

        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (normalDirect) {
                    normalDirect = false;
                    Toast.makeText(RouteActivity.this, "Ngược", Toast.LENGTH_SHORT).show();
                    listStationAdapter = new ListStationAdapter(RouteActivity.this, R.layout.list_station_item, listReverseRoute);
                    listStation.setAdapter(listStationAdapter);
                } else {
                    normalDirect = true;
                    Toast.makeText(RouteActivity.this, "Xuôi", Toast.LENGTH_SHORT).show();
                    listStationAdapter = new ListStationAdapter(RouteActivity.this, R.layout.list_station_item, listNormalRoute);
                    listStation.setAdapter(listStationAdapter);
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stationSelected = null;
                listStation.requestLayout();
                listStation.clearChoices();
                listStationAdapter.notifyDataSetChanged();
            }
        });

        listStation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationSelected = (String) listStationAdapter.getItem(position);
                listStationAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {
        myMap = map;

        double[] startLatLng, endLatLng;
        startLatLng = db.getStationLatLngByName(listNormalRoute.get(0));
        endLatLng = db.getStationLatLngByName(listNormalRoute.get(listNormalRoute.size() - 1));

        LatLng startLocation = new LatLng(startLatLng[0], startLatLng[1]);
        LatLng endLocation = new LatLng(endLatLng[0], endLatLng[1]);

        myMap.addMarker(new MarkerOptions()
                .position(startLocation)
                .title("Điểm đầu")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        myMap.addMarker(new MarkerOptions()
                .position(endLocation)
                .title("Điểm cuối"));

        for (int i = 1;i < listNormalRoute.size() - 1;i++) {
            double[] locationLatLng;
            locationLatLng = db.getStationLatLngByName(listNormalRoute.get(i));
            LatLng location = new LatLng(locationLatLng[0], locationLatLng[1]);

            myMap.addMarker(new MarkerOptions()
                    .position(location)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        }

        myMap.setMyLocationEnabled(true);

        new ParseXML().execute();

        final LatLngBounds.Builder boundsBuilder = LatLngBounds.builder().include(startLocation).include(endLocation);

        myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 60));
                myMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        //LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        if (stationSelected != null) {
                            double[] arrLatLng = db.getStationLatLngByName(stationSelected);
                            //LatLng stationTargetLatLng = new LatLng(arrLatLng[0], arrLatLng[1]);
                            Location stationTarget = new Location("target");
                            stationTarget.setLatitude(arrLatLng[0]);
                            stationTarget.setLongitude(arrLatLng[1]);

                            float distance = location.distanceTo(stationTarget);

                            if (distance <= 500) {
                                createNotification();
                                stationSelected = null;
                                listStation.requestLayout();
                                listStation.clearChoices();
                                listStationAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });

    }

    private void createNotification() {
        int mId = 1;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_bus_white)
                .setContentTitle("Sắp đến điểm dừng")
                .setContentText("Sắp đến rồi, xuống nhanh lên còn kịp")
                .setSound(alarmSound);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mId, mBuilder.build());
    }

    private class ParseXML extends AsyncTask<Void, Void, Document> {
        Document doc;
        PolylineOptions rectLine;

        @Override
        protected Document doInBackground(Void... params) {
            md = new GMapV2Direction();

            rectLine = new PolylineOptions()
                    .width(10)
                    .color(Color.BLUE);

            ArrayList<LatLng> listLatLng = new ArrayList<>();
            for (int i = 0;i < listNormalRoute.size();i++) {
                double[] locationLatLng;
                locationLatLng = db.getStationLatLngByName(listNormalRoute.get(i));
                LatLng location = new LatLng(locationLatLng[0], locationLatLng[1]);

                listLatLng.add(location);
            }

            for (int i = 0;i < listLatLng.size() - 1;i++) {
                doc = md.getDocument(listLatLng.get(i), listLatLng.get(i+1),
                        GMapV2Direction.MODE_DRIVING);
                ArrayList<LatLng> directionPoint = md.getDirection(doc);

                for (int j = 0;j < directionPoint.size();j++) {
                    rectLine.add(directionPoint.get(j));
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(Document result) {
            myMap.addPolyline(rectLine);
        }

    }

}
