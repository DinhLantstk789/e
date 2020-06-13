package com.example.mobilesporta.activity.club;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesporta.R;

public class FootballMatchInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_football_match_info);
    }
}
