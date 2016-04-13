package com.example.linhlee.myibus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linhlee.myibus.R;

import org.w3c.dom.Text;

/**
 * Created by Linh Lee on 4/13/2016.
 */
public class RouteActivity extends AppCompatActivity {

    private String titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        TextView title = (TextView) findViewById(R.id.text_title);
        ImageView backButton = (ImageView) findViewById(R.id.btn_back);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titleName = extras.getString("title");
        }
        title.setText(titleName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
