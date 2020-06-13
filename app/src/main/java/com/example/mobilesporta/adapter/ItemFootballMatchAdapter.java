package com.example.mobilesporta.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class    ItemFootballMatchAdapter extends BaseAdapter {

    private Activity activity;
    private List<MatchModel> FootballMatchList;
    Map<String, ClubModel> mapClubs = new HashMap<>();

    public ItemFootballMatchAdapter(Activity activity, List<MatchModel> footballMatchList, Map<String, ClubModel> mapClubs) {
        this.activity = activity;
        FootballMatchList = footballMatchList;
        this.mapClubs = mapClubs;
    }

    public ClubModel getClubById(String clubId) {
        ClubModel clubModel = new ClubModel();
        for (Map.Entry<String, ClubModel> entry : mapClubs.entrySet()) {
            if(entry.getKey().equals(clubId)) {
                clubModel = entry.getValue();
            }
        }
        return  clubModel;
    }

    @Override
    public int getCount() {
        return FootballMatchList.size();
    }

    @Override
    public Object getItem(int position) {
        return FootballMatchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // tung itemt
        // goi layoutInflater ra để bắt đầu ánh xạ view và data
        LayoutInflater inflater = activity.getLayoutInflater();

        // Đổ dữ liệu vào convertView, chính là những gì nằm trong item_name/xml
        convertView = inflater.inflate(R.layout.item_football_match, null);

        // đặt chữ cho từng view trong danh sách
        ImageView imgHomeClub = convertView.findViewById(R.id.imgItemFootballMatch_HomeClubAvt);
        TextView txtHomeClubName = convertView.findViewById(R.id.txtItemFootballMatch_HomeClubName);

        ImageView imgAwayClub = convertView.findViewById(R.id.imgItemFootballMatch_AwayClubAvt);
        TextView txtAwayClubName = convertView.findViewById(R.id.txtItemFootballMatch_AwayClubName);

        TextView txtMatchTime = convertView.findViewById(R.id.txtItemFootballMatch_Time);
        TextView txtMatchDate = convertView.findViewById(R.id.txtItemFootballMatch_Date);
        TextView txtStadium = convertView.findViewById(R.id.txtItemFootballMatch_Stadium);

        MatchModel match = FootballMatchList.get(position);

        txtHomeClubName.setText(match.getClub_away_id());
        txtAwayClubName.setText(match.getClub_away_id());
        txtMatchTime.setText(match.getTime());
        txtMatchDate.setText(match.getDate());
        txtStadium.setText(match.getStadium_id());


        if (!match.getClub_home_id().equals("")) {
            ClubModel homeClub = getClubById(match.getClub_home_id());
            txtHomeClubName.setText(homeClub.getClub_name());
            Picasso.get().load(homeClub.getImage()).into(imgHomeClub);
        }
        else {
            txtHomeClubName.setText("???");
        }


        if( !match.getClub_away_id().equals("")) {
            ClubModel awayClub = getClubById(match.getClub_away_id());
            txtAwayClubName.setText(awayClub.getClub_name());
            Picasso.get().load(awayClub.getImage()).into(imgAwayClub);
        }
        else {
            txtAwayClubName.setText("???");
        }


        return convertView;
    }
}
