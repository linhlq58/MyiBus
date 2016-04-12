package com.example.linhlee.myibus.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.linhlee.myibus.R;
import com.example.linhlee.myibus.activities.MainActivity;
import com.example.linhlee.myibus.objects.BusItem;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/11/2016.
 */
public class ListBusAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private ArrayList<BusItem> busItems;

    public ListBusAdapter(MainActivity context, int layout, ArrayList<BusItem> listBusItems) {
        this.context = context;
        this.layout = layout;
        this.busItems = listBusItems;
    }

    @Override
    public int getCount() {
        return busItems.size();
    }

    @Override
    public Object getItem(int position) {
        return busItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, layout, null);
        }

        TextView busNumber = (TextView) convertView.findViewById(R.id.bus_number);
        TextView busRoute = (TextView) convertView.findViewById(R.id.bus_route);

        busNumber.setText(busItems.get(position).getBusNumber() + "");
        busRoute.setText(busItems.get(position).getBusName());

        return convertView;
    }
}
