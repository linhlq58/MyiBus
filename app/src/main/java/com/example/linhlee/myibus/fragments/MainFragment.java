package com.example.linhlee.myibus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linhlee.myibus.R;
import com.example.linhlee.myibus.activities.MainActivity;
import com.example.linhlee.myibus.activities.RouteActivity;
import com.example.linhlee.myibus.adapters.ListBusAdapter;
import com.example.linhlee.myibus.database.DataBaseHelper;
import com.example.linhlee.myibus.objects.BusItem;
import com.example.linhlee.myibus.objects.NormalRouteItem;
import com.example.linhlee.myibus.objects.ReverseRouteItem;
import com.example.linhlee.myibus.objects.StationItem;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/11/2016.
 */
public class MainFragment extends Fragment {
    private ListView listView;
    private ListBusAdapter listBusAdapter;
    private ArrayList<BusItem> listBusItems;

    private ArrayList<String> arrayReferenceString;

    private EditText searchText;
    private Button searchButton;

    private DataBaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        searchText = (EditText) rootView.findViewById(R.id.search_text);
        searchButton = (Button) rootView.findViewById(R.id.btn_search);
        listView = (ListView) rootView.findViewById(R.id.list_bus);

        initData();
        listBusItems = db.getAllBus();
        for (int i=0;i<listBusItems.size();i++) {
            listBusItems.get(i).setNormalRoute(db.getNormalRouteStationByBusNumber(listBusItems.get(i).getBusNumber()));
            listBusItems.get(i).setReverseRoute(db.getReverseRouteStationByBusNumber(listBusItems.get(i).getBusNumber()));
        }

        arrayReferenceString = new ArrayList<>();
        for (int i=0;i<listBusItems.size();i++) {
            arrayReferenceString.add(listBusItems.get(i).getBusNumber()
                    + " " + listBusItems.get(i).getBusName());
            arrayReferenceString.get(0).toLowerCase();
        }

        listBusAdapter = new ListBusAdapter((MainActivity) getActivity(), R.layout.list_bus_item, listBusItems);

        listView.setAdapter(listBusAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent((MainActivity) getActivity(), RouteActivity.class);
                intent.putExtra("title", ((BusItem) listBusAdapter.getItem(position)).getBusName());
                intent.putExtra("listNormalRoute", ((BusItem) listBusAdapter.getItem(position)).getNormalRoute());
                intent.putExtra("listReverseRoute", ((BusItem) listBusAdapter.getItem(position)).getReverseRoute());

                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = searchText.getText().toString();
                listBusAdapter.getFilter().filter(searchString);
            }
        });

        /*searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listBusAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        return rootView;
    }

    public void initData() {
        db = new DataBaseHelper((MainActivity)getActivity());

        db.addBus(new BusItem(32, "Giáp Bát - Nhổn"));
        db.addBus(new BusItem(14, "Đầu này - Đầu kia"));
        db.addBus(new BusItem(22, "Việt Nam - USA"));
        db.addBus(new BusItem(10, "Trái Đất - Sao Hỏa"));

        db.addStation(new StationItem(1, "Cầu Giấy", 128, 256));
        db.addStation(new StationItem(2, "Thanh Xuân", 243, 194));
        db.addStation(new StationItem(3, "Xuân Thủy", 111, 222));
        db.addStation(new StationItem(4, "Hoàng Mai", 143, 153));
        db.addStation(new StationItem(5, "Đống Đa", 94, 47));
        db.addStation(new StationItem(6, "Thanh Liêm", 102, 152));
        db.addStation(new StationItem(7, "BX Giáp Bát", 142, 131));
        db.addStation(new StationItem(8, "Nhổn", 192, 129));

        /*db.addNormalRoute(new NormalRouteItem(32, 7));
        db.addNormalRoute(new NormalRouteItem(32, 4));
        db.addNormalRoute(new NormalRouteItem(32, 3));
        db.addNormalRoute(new NormalRouteItem(32, 1));
        db.addNormalRoute(new NormalRouteItem(32, 2));
        db.addNormalRoute(new NormalRouteItem(32, 8));

        db.addReverseRoute(new ReverseRouteItem(32, 8));
        db.addReverseRoute(new ReverseRouteItem(32, 2));
        db.addReverseRoute(new ReverseRouteItem(32, 5));
        db.addReverseRoute(new ReverseRouteItem(32, 3));
        db.addReverseRoute(new ReverseRouteItem(32, 4));
        db.addReverseRoute(new ReverseRouteItem(32, 7));*/

    }

}
