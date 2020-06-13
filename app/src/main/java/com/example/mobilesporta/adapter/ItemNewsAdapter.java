package com.example.mobilesporta.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemNewsAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NewsModel> listNews;

    public ItemNewsAdapter(Context context, int layout, List<NewsModel> listNews) {
        this.context = context;
        this.layout = layout;
        this.listNews = listNews;
    }

    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int position) {
        return listNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        TextView txtTitle = convertView.findViewById(R.id.tv_title);
        TextView txtDesc = convertView.findViewById(R.id.tv_desc);
        ImageView imgNews = convertView.findViewById(R.id.image_news);

        NewsModel newsModel = listNews.get(position);

        txtTitle.setText(newsModel.getTitle());
        txtDesc.setText(newsModel.getDesc());
        Picasso.get().load(newsModel.getImage()).into(imgNews);


        return convertView;
    }
}
