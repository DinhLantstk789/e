package com.example.mobilesporta.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.ClubModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemClubAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ClubModel> clubList;

    public ItemClubAdapter(Context context, int layout, List<ClubModel> clubList) {
        this.context = context;
        this.layout = layout;
        this.clubList = clubList;
    }

    @Override
    public int getCount() {
        return clubList.size();
    }

    @Override
    public Object getItem(int position) {
        return clubList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        TextView txtNameClub = convertView.findViewById(R.id.txtItemClub_ClubName);
        TextView txtSlogan = convertView.findViewById(R.id.txtItemClub_slogan);
        ImageView imgClub = convertView.findViewById(R.id.imgItemClub_ClubImage);

        ClubModel clubModel = clubList.get(position);

        txtNameClub.setText(clubModel.getClub_name());
        txtSlogan.setText(clubModel.getSlogan());
        Picasso.get().load(clubModel.getImage()).into(imgClub);


        return convertView;
    }

}
