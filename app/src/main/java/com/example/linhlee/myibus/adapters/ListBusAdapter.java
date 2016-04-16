package com.example.linhlee.myibus.adapters;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.linhlee.myibus.R;
import com.example.linhlee.myibus.activities.MainActivity;
import com.example.linhlee.myibus.activities.RouteActivity;
import com.example.linhlee.myibus.objects.BusItem;
import com.example.linhlee.myibus.utils.RemoveAccent;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/11/2016.
 */
public class ListBusAdapter extends BaseAdapter implements Filterable {
    private MainActivity context;
    private int layout;
    private ArrayList<BusItem> busItemsOriginal;
    private ArrayList<BusItem> busItemsDisplayed;

    public ListBusAdapter(MainActivity context, int layout, ArrayList<BusItem> listBusItems) {
        this.context = context;
        this.layout = layout;
        this.busItemsOriginal = listBusItems;
        this.busItemsDisplayed = listBusItems;
    }

    @Override
    public int getCount() {
        return busItemsDisplayed.size();
    }

    @Override
    public Object getItem(int position) {
        return busItemsDisplayed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(layout, parent, false);

        }

        final LinearLayout busLayout = (LinearLayout) convertView.findViewById(R.id.list_bus_layout);
        TextView busNumber = (TextView) convertView.findViewById(R.id.bus_number);
        TextView busRoute = (TextView) convertView.findViewById(R.id.bus_route);

        busNumber.setText(busItemsDisplayed.get(position).getBusNumber() + "");
        busRoute.setText(busItemsDisplayed.get(position).getBusName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<BusItem> filteredArrList = new ArrayList<BusItem>();

                if (busItemsOriginal == null) {
                    busItemsOriginal = new ArrayList<BusItem>(busItemsDisplayed);
                }

                if (constraint == null) {
                    results.count = busItemsOriginal.size();
                    results.values = busItemsOriginal;
                } else {
                    constraint = RemoveAccent.removeAccent(constraint.toString().toLowerCase());
                    for (int i = 0;i < busItemsOriginal.size();i++) {
                        ArrayList<String> arrayNormalRoute = busItemsOriginal.get(i).getNormalRoute();
                        ArrayList<String> arrayReverseRoute = busItemsOriginal.get(i).getReverseRoute();
                        String dataNormalRoute = "";
                        String dataReverseRoute = "";

                        for(int j = 0;j < arrayNormalRoute.size();j++) {
                            dataNormalRoute = dataNormalRoute + arrayNormalRoute.get(j).toString() + " ";
                        }

                        for(int j = 0;j < arrayReverseRoute.size();j++) {
                            dataReverseRoute = dataReverseRoute + arrayReverseRoute.get(j).toString() + " ";
                        }

                        String data = busItemsOriginal.get(i).getBusNumber() + " "
                                + busItemsOriginal.get(i).getBusName() + " "
                                + dataNormalRoute.toString() + dataReverseRoute.toString();
                        data = RemoveAccent.removeAccent(data.toLowerCase());

                        if (data.indexOf(constraint.toString()) > -1) {
                            BusItem bus = new BusItem(busItemsOriginal.get(i).getBusNumber(),
                                    busItemsOriginal.get(i).getBusName());
                            bus.setNormalRoute(arrayNormalRoute);
                            bus.setReverseRoute(arrayReverseRoute);
                            filteredArrList.add(bus);
                        }
                    }

                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                busItemsDisplayed = (ArrayList<BusItem>) results.values;
                notifyDataSetChanged();

            }
        };

        return filter;
    }
}
