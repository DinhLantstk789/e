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
import com.example.mobilesporta.model.StadiumModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemStadiumForMatchAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<StadiumModel> listStadium;

    public ItemStadiumForMatchAdapter(Context context, int layout, List<StadiumModel> listStadium) {
        this.context = context;
        this.layout = layout;
        this.listStadium = listStadium;
    }

    @Override
    public int getCount() {
        return listStadium.size();
    }

    @Override
    public Object getItem(int position) {
        return listStadium.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        ImageView imageStadium = convertView.findViewById(R.id.img_stadium_for_match);
        TextView nameStadium = convertView.findViewById(R.id.tv_name_stadium_for_match);
        TextView addressStadium = convertView.findViewById(R.id.tv_address_stadium);
        TextView openingTime = convertView.findViewById(R.id.tv_time_stadium);

        Picasso.get().load(listStadium.get(position).getImage()).into(imageStadium);
        nameStadium.setText(listStadium.get(position).getStadium_name());
        addressStadium.setText(listStadium.get(position).getAddress());
        openingTime.setText(listStadium.get(position).getTime_open() + " - " + listStadium.get(position).getTime_close());

        return convertView;
    }
}
