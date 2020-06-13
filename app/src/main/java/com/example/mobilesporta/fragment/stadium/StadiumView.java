package com.example.mobilesporta.fragment.stadium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;

public class StadiumView extends AppCompatActivity {
    TextView textTitle;
    Button findWay,backStadiumView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium_view);

        textTitle = findViewById(R.id.stadiumViewName);
        Intent i = getIntent();
        String text = i.getStringExtra("title");
        textTitle.setText(text);
        findWay = findViewById(R.id.findWay);
        backStadiumView = findViewById(R.id.backStadiumView);
        findWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),StadiumMap.class);
                startActivity(i);
            }
        });

        backStadiumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Home.class);
                i.putExtra("main","true");
                startActivity(i);
            }
        });
    }
}