package com.example.linhlee.myibus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linhlee.myibus.R;
import com.example.linhlee.myibus.adapters.ListStationAdapter;
import com.example.linhlee.myibus.database.DataBaseHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    private ListView listStation;
    private ListStationAdapter listStationAdapter;

    private boolean normalDirect = true;

    private GoogleMap myMap;

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


    }

    @Override
    public void onMapReady(GoogleMap map) {
        myMap = map;
        myMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
