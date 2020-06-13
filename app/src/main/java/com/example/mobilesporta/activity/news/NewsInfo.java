package com.example.mobilesporta.activity.news;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.mobilesporta.R;

public class NewsInfo extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView = findViewById(R.id.wv_news_info);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);

    }

}
