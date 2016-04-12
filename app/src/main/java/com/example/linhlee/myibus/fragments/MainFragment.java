package com.example.linhlee.myibus.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.linhlee.myibus.R;
import com.example.linhlee.myibus.activities.MainActivity;
import com.example.linhlee.myibus.adapters.ListBusAdapter;
import com.example.linhlee.myibus.database.DataBaseHelper;
import com.example.linhlee.myibus.objects.BusItem;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/11/2016.
 */
public class MainFragment extends Fragment {
    private ListView listView;
    private ListBusAdapter listBusAdapter;
    private ArrayList<BusItem> listBusItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_bus);

        initArray();

        listBusAdapter = new ListBusAdapter((MainActivity)getActivity(), R.layout.list_bus_item, listBusItems);

        listView.setAdapter(listBusAdapter);

        return rootView;
    }

    public void initArray() {
        DataBaseHelper db = new DataBaseHelper((MainActivity)getActivity());

        db.addBus(new BusItem(32, "Giáp Bát - Nhổn"));
        db.addBus(new BusItem(14, "Đầu này - Đầu kia"));
        db.addBus(new BusItem(22, "Việt Nam - USA"));
        db.addBus(new BusItem(10, "Trái Đất - Sao Hỏa"));

        listBusItems = db.getAllBus();

        /*for (int i=0;i<listBusItems.size();i++) {
            db.deleteBus(listBusItems.get(i));
        }*/

    }
}
