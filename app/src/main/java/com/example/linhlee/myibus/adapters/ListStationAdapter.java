package com.example.linhlee.myibus.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.linhlee.myibus.R;
import com.example.linhlee.myibus.activities.MainActivity;
import com.example.linhlee.myibus.activities.RouteActivity;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/13/2016.
 */
public class ListStationAdapter extends BaseAdapter {
    private RouteActivity context;
    private int layout;
    private ArrayList<String> listStation;

    public ListStationAdapter(RouteActivity context, int layout, ArrayList<String> listStation) {
        this.context = context;
        this.layout = layout;
        this.listStation = listStation;
    }

    @Override
    public int getCount() {
        return listStation.size();
    }

    @Override
    public Object getItem(int position) {
        return listStation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(layout, parent, false);
        }

        TextView stationName = (TextView) context.findViewById(R.id.text_station);
        stationName.setText(listStation.get(position));

        return convertView;
    }
}
