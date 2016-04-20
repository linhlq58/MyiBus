package com.example.linhlee.myibus.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        initData();
        listBusItems = db.getAllBus();
        for (int i = 0; i < listBusItems.size();i++) {
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

        db.addBus(new BusItem("01", "BX Gia Lâm - BX Yên Nghĩa"));
        db.addBus(new BusItem("02", "Bác Cổ - BX Yên Nghĩa"));
        db.addBus(new BusItem("03", "BX Giáp Bát - BX Gia Lâm"));
        db.addBus(new BusItem("03B", "BX Giáp Bát - TTTM Vincom Village"));
        db.addBus(new BusItem("04", "Long Biên - BX Nước Ngầm"));
        db.addBus(new BusItem("05", "Linh Đàm - Phú Diễn"));
        db.addBus(new BusItem("06A", "BX Giáp Bát - Cầu Giẽ"));

        db.addStation(new StationItem(1, "BX Gia Lâm", 21.0471648, 105.8769165));
        db.addStation(new StationItem(2, "Ngô Gia Khảm", 21.0481135, 105.8772487));
        db.addStation(new StationItem(3, "Ngọc Lâm", 21.0486696, 105.8731703));
        db.addStation(new StationItem(4, "Nguyễn Văn Cừ", 21.042912, 105.8688683));
        db.addStation(new StationItem(5, "Cầu Chương Dương", 21.038516, 105.8603063));
        db.addStation(new StationItem(6, "Trần Nhật Duật", 21.037805, 105.8506673));
        db.addStation(new StationItem(7, "Yên Phụ", 21.041188, 105.8478823));
        db.addStation(new StationItem(8, "Điểm trung chuyển Long Biên", 21.0414487, 105.8473564));
        db.addStation(new StationItem(9, "Hàng Đậu", 21.040237, 105.8462403));
        db.addStation(new StationItem(10, "Hàng Cót", 21.037253, 105.8449313));
        db.addStation(new StationItem(11, "Hàng Gà", 21.03475, 105.8448243));
        db.addStation(new StationItem(12, "Hàng Điếu", 21.033098, 105.8447383));
        db.addStation(new StationItem(13, "Đường Thành", 21.031305, 105.8448023));
        db.addStation(new StationItem(14, "Phủ Doãn", 21.028922, 105.8455533));
        db.addStation(new StationItem(15, "Triệu Quốc Đạt", 21.0266925, 105.8465342));
        db.addStation(new StationItem(16, "Hai Bà Trưng", 21.0268225, 105.8429457));
        db.addStation(new StationItem(17, "Lê Duẩn", 21.0256055, 105.8403867));
        db.addStation(new StationItem(18, "Khâm Thiên", 21.0190455, 105.8365457));
        db.addStation(new StationItem(19, "Đường mới (vành đai 1)", 0, 0));
        db.addStation(new StationItem(20, "Nguyễn Lương Bằng", 21.0158155, 105.8271417));
        db.addStation(new StationItem(21, "Tây Sơn", 21.0074425, 105.8222387));
        db.addStation(new StationItem(22, "Ngã Tư Sở", 21.0028953, 105.8191595));
        db.addStation(new StationItem(23, "Nguyễn Trãi", 20.9977975, 105.8110377));
        db.addStation(new StationItem(24, "Trần Phú (Hà Đông)", 20.9805135, 105.7869787));
        db.addStation(new StationItem(25, "Quang Trung (Hà Đông)", 20.9702875, 105.7737717));
        db.addStation(new StationItem(26, "Ba La", 20.9550493, 105.7542141));
        db.addStation(new StationItem(27, "Quốc lộ 6", 20.951826, 105.7487393));
        db.addStation(new StationItem(28, "BX Yên Nghĩa", 20.9502159, 105.7450316));
        db.addStation(new StationItem(29, "Xã Đàn", 21.0136647, 105.8327989));
        db.addStation(new StationItem(30, "Nguyễn Thượng Hiền", 21.0188671, 105.8408663));
        db.addStation(new StationItem(31, "Yết Kiêu", 21.0223225,105.8422447));
        db.addStation(new StationItem(32, "Trần Hưng Đạo", 21.0234175, 105.8428717));
        db.addStation(new StationItem(33, "Quán Sứ", 21.0255755, 105.8442077));
        db.addStation(new StationItem(34, "Hàng Da", 21.0305825, 105.8455487));
        db.addStation(new StationItem(35, "Phùng Hưng", 21.0360105, 105.8449107));
        db.addStation(new StationItem(36, "Lê Văn Linh", 21.0373625, 105.8450607));
        db.addStation(new StationItem(37, "Phùng Hưng (Đường trong)", 21.0389095,105.8454737));
        db.addStation(new StationItem(38, "Phan Đình Phùng", 21.0398955, 105.8458547));
        db.addStation(new StationItem(39, "Hàng Đậu", 21.0402355, 105.8473187));
        db.addStation(new StationItem(40, "Bác Cổ", 21.0245724, 105.8581366));
        db.addStation(new StationItem(41, "BX Giáp Bát", 20.9803916, 105.8396844));
        db.addStation(new StationItem(42, "TTTM Vincom Village", 21.0507406, 105.9141897));
        db.addStation(new StationItem(99, "Địa điểm test", 21.0327814, 105.7815419));


        db.addNormalRoute(new NormalRouteItem("01", 1));
        db.addNormalRoute(new NormalRouteItem("01", 2));
        db.addNormalRoute(new NormalRouteItem("01", 3));
        db.addNormalRoute(new NormalRouteItem("01", 4));
        db.addNormalRoute(new NormalRouteItem("01", 5));
        db.addNormalRoute(new NormalRouteItem("01", 6));
        db.addNormalRoute(new NormalRouteItem("01", 7));
        db.addNormalRoute(new NormalRouteItem("01", 8));
        db.addNormalRoute(new NormalRouteItem("01", 9));
        db.addNormalRoute(new NormalRouteItem("01", 10));
        db.addNormalRoute(new NormalRouteItem("01", 11));
        db.addNormalRoute(new NormalRouteItem("01", 12));
        db.addNormalRoute(new NormalRouteItem("01", 13));
        db.addNormalRoute(new NormalRouteItem("01", 14));
        db.addNormalRoute(new NormalRouteItem("01", 15));
        db.addNormalRoute(new NormalRouteItem("01", 16));
        db.addNormalRoute(new NormalRouteItem("01", 17));
        db.addNormalRoute(new NormalRouteItem("01", 18));
        db.addNormalRoute(new NormalRouteItem("01", 20));
        db.addNormalRoute(new NormalRouteItem("01", 21));
        db.addNormalRoute(new NormalRouteItem("01", 22));
        db.addNormalRoute(new NormalRouteItem("01", 23));
        db.addNormalRoute(new NormalRouteItem("01", 24));
        db.addNormalRoute(new NormalRouteItem("01", 25));
        db.addNormalRoute(new NormalRouteItem("01", 26));
        db.addNormalRoute(new NormalRouteItem("01", 28));
        db.addNormalRoute(new NormalRouteItem("02", 40));
        db.addNormalRoute(new NormalRouteItem("02", 28));
        db.addNormalRoute(new NormalRouteItem("03", 41));
        db.addNormalRoute(new NormalRouteItem("03", 1));
        db.addNormalRoute(new NormalRouteItem("03B", 41));
        db.addNormalRoute(new NormalRouteItem("03B", 99));


        db.addReverseRoute(new ReverseRouteItem("01", 28));
        db.addReverseRoute(new ReverseRouteItem("01", 26));
        db.addReverseRoute(new ReverseRouteItem("01", 25));
        db.addReverseRoute(new ReverseRouteItem("01", 24));
        db.addReverseRoute(new ReverseRouteItem("01", 23));
        db.addReverseRoute(new ReverseRouteItem("01", 22));
        db.addReverseRoute(new ReverseRouteItem("01", 21));
        db.addReverseRoute(new ReverseRouteItem("01", 20));
        db.addReverseRoute(new ReverseRouteItem("01", 29));
        db.addReverseRoute(new ReverseRouteItem("01", 18));
        db.addReverseRoute(new ReverseRouteItem("01", 30));
        db.addReverseRoute(new ReverseRouteItem("01", 31));
        db.addReverseRoute(new ReverseRouteItem("01", 32));
        db.addReverseRoute(new ReverseRouteItem("01", 33));
        db.addReverseRoute(new ReverseRouteItem("01", 34));
        db.addReverseRoute(new ReverseRouteItem("01", 13));
        db.addReverseRoute(new ReverseRouteItem("01", 35));
        db.addReverseRoute(new ReverseRouteItem("01", 36));
        db.addReverseRoute(new ReverseRouteItem("01", 37));
        db.addReverseRoute(new ReverseRouteItem("01", 38));
        db.addReverseRoute(new ReverseRouteItem("01", 39));
        db.addReverseRoute(new ReverseRouteItem("01", 6));
        db.addReverseRoute(new ReverseRouteItem("01", 5));
        db.addReverseRoute(new ReverseRouteItem("01", 4));
        db.addReverseRoute(new ReverseRouteItem("01", 3));
        db.addReverseRoute(new ReverseRouteItem("01", 2));
        db.addReverseRoute(new ReverseRouteItem("01", 1));
        db.addReverseRoute(new ReverseRouteItem("02", 28));
        db.addReverseRoute(new ReverseRouteItem("02", 40));
        db.addReverseRoute(new ReverseRouteItem("03", 1));
        db.addReverseRoute(new ReverseRouteItem("03", 41));
        db.addReverseRoute(new ReverseRouteItem("03B", 99));
        db.addReverseRoute(new ReverseRouteItem("03B", 41));


    }

}
