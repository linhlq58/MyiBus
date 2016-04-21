package com.example.linhlee.myibus.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.linhlee.myibus.activities.AboutActivity;
import com.example.linhlee.myibus.utils.GMapV2Direction;
import com.example.linhlee.myibus.R;
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

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/11/2016.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap myMap;
    private GMapV2Direction md;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)
                this.getChildFragmentManager().findFragmentById(R.id.map_main);
        mapFragment.getMapAsync(this);

        Button aboutButton = (Button) rootView.findViewById(R.id.btn_about_maps);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        myMap = map;

        myMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (myMap != null) {
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16.0f));
                }
            }
        });

        myMap.setMyLocationEnabled(true);

    }

}
