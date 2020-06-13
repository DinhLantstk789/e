package com.example.mobilesporta.fragment.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.news.NewsInfo;
import com.example.mobilesporta.adapter.ItemNewsAdapter;
import com.example.mobilesporta.data.service.NewsService;
import com.example.mobilesporta.model.NewsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private TextView txtTitle;
    ListView listView;
    EditText edtSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_news, container, false);

        listView = view.findViewById(R.id.lv_item_news);
        edtSearch = view.findViewById(R.id.search_news);

        searchNews();
        showListNews();

        return view;
    }

    private void searchNews() {
        final ArrayList<NewsModel> listNews = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("news");
        db.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        NewsModel news = snapshot.getValue(NewsModel.class);
                        listNews.add(news);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<NewsModel> newList = new ArrayList<>();
                for (int i = 0 ; i < listNews.size() ; i++){
                    if (listNews.get(i).getTitle().toLowerCase().contains(s)){
                        newList.add(listNews.get(i));
                    }
                }

                ItemNewsAdapter itemNewsAdapter = new ItemNewsAdapter(getContext(), R.layout.news_listview, listNews);
                listView.setAdapter(itemNewsAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), NewsInfo.class);
                        intent.putExtra("url", listNews.get(position).getUrl());
                        startActivity(intent);
                    }
                });

                itemNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showListNews() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("news");
        databaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    final ArrayList<NewsModel> listNews = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        NewsModel newsModel = snapshot.getValue(NewsModel.class);
                        listNews.add(newsModel);
                    }
                    ItemNewsAdapter itemNewsAdapter = new ItemNewsAdapter(getContext(), R.layout.news_listview, listNews);
                    listView.setAdapter(itemNewsAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(), NewsInfo.class);
                            intent.putExtra("url", listNews.get(position).getUrl());
                            startActivity(intent);
                        }
                    });

                    itemNewsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}