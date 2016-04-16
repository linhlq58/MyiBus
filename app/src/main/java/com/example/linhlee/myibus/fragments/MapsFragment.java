package com.example.linhlee.myibus.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private static final LatLng GiapBatStation = new LatLng(20.9803916,105.8396844);
    private static final LatLng otherLocation = new LatLng(21.0521175,105.7341438);

    //private String encodedString ="mv`_Cmy~dSm@Ca@K_A[aA]gAWaAMeCKYIECCE?w@?g@|HFhJF|RDrRJjE?@X@^~@fIR|@JpBT`FPnDAHbCBDrCFp@RdAnDnOtA~GBpBA`ATr@Vl@Il@Mt@w@nB}BpF_AvCs@vCy@pDwApHiAnFcCjL_AbDq@hBcAvB{@zA_ApAwCjDk@f@cBtAqBzAsMpI}A`AwAbAaDrBsCrB}DnC{FvDaStMwAz@sEtCyCnByD|B{FfE}@p@qG|EoE`DAFKNeKrHGLADy\\\\vVaNhKkNfKgWrR_PtLeKzH}OzLoEbD{AfAmAbAgCtBwB~A}BfBiAr@cD~As@Va@LeBf@sAV{Db@gEDiCMcDYqHs@oD[iRqBMKuAQ[KGEwBWuLiAuC_@qBa@WC[FHtBJrADnAGfDA~@IbAOzAOnAoBbJYhAeApFkAnHgAvFyA|GoAbHIn@C@EBCFAF?H@D{DfQ{FzUcClJcElO_BxFwF~Sk@nBuFdReCfIg@hBe@hBuHbWm@|BaDxKsA`Eu@tAkCzEcCjD_BvCmAlBaCbE{@vAp@YZIpCu@tEuA";

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



        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        myMap = map;

        myMap.addMarker(new MarkerOptions()
                .position(otherLocation)
                .title("Bla bla bla")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        myMap.addMarker(new MarkerOptions()
                .position(GiapBatStation)
                .title("Marker in Giap Bat"));
        myMap.setMyLocationEnabled(true);

        new ParseXML().execute();

        LatLngBounds.Builder boundsBuilder = LatLngBounds.builder().include(GiapBatStation).include(otherLocation);
        myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 60));


    }

    private class ParseXML extends AsyncTask<Void, Void, Document> {
        Document doc;
        PolylineOptions rectLine;
        @Override
        protected Document doInBackground(Void... params) {
            md = new GMapV2Direction();
            doc = md.getDocument(GiapBatStation, otherLocation,
                    GMapV2Direction.MODE_DRIVING);
            ArrayList<LatLng> directionPoint = md.getDirection(doc);
            rectLine = new PolylineOptions().width(10).color(
                    Color.BLUE);

            for (int i = 0; i < directionPoint.size(); i++) {
                rectLine.add(directionPoint.get(i));
            }
            return null;

        }

        @Override
        protected void onPostExecute(Document result) {
            myMap.addPolyline(rectLine);
        }

    }

}
