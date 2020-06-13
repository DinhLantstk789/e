package com.example.mobilesporta.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemHistoryClubAdapter extends BaseAdapter {
    private Activity activity;
    private List<MatchModel> FootballMatchList;
    ImageView imgHomeClub;
    TextView txtHomeClubName;
    ImageView imgAwayClub;
    TextView txtAwayClubName;
    TextView txtMatchTime;
    TextView txtMatchDate;
    TextView txtStadium;
    private ArrayList<ClubModel> listClub = new ArrayList<>();


    public ItemHistoryClubAdapter(Activity activity, List<MatchModel> FootballMatchList) {
        this.activity = activity;
        this.FootballMatchList = FootballMatchList;
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
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_history_club, null);
        // đặt chữ cho từng view trong danh sách
        imgHomeClub = convertView.findViewById(R.id.imgItemFootballMatch_HomeClubAvt);
        txtHomeClubName = convertView.findViewById(R.id.txtItemFootballMatch_HomeClubName);
        imgAwayClub = convertView.findViewById(R.id.imgItemFootballMatch_AwayClubAvt);
        txtAwayClubName = convertView.findViewById(R.id.txtItemFootballMatch_AwayClubName);
        txtMatchTime = convertView.findViewById(R.id.txtItemFootballMatch_Time);
        txtMatchDate = convertView.findViewById(R.id.txtItemFootballMatch_Date);
        txtStadium = convertView.findViewById(R.id.txtItemFootballMatch_Stadium);

        txtMatchDate.setText(FootballMatchList.get(position).getDate() + " - " + FootballMatchList.get(position).getTime());
        txtStadium.setText(FootballMatchList.get(position).getStadium_id());

        String clubHomeId = FootballMatchList.get(position).getClub_home_id();
        String clubAwayId = FootballMatchList.get(position).getClub_away_id();

        renderClubHome(clubHomeId, txtHomeClubName, imgHomeClub);

        renderAwayClub(clubAwayId, txtAwayClubName, imgAwayClub);

        return convertView;
    }

    private void renderClubHome(String clubHomeId, final TextView textView, final ImageView imageView){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query infoClubHome = mDatabase.orderByKey().equalTo(clubHomeId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        textView.setText(clubModel.getClub_name());
                        Picasso.get().load(clubModel.getImage()).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        infoClubHome.addListenerForSingleValueEvent(valueEventListener);
    }

    private void renderAwayClub(String clubAwayId, final TextView textView, final ImageView imageView){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query infoClubAway = mDatabase.orderByKey().equalTo(clubAwayId);
        ValueEventListener clubAway = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ClubModel club = snapshot.getValue(ClubModel.class);
                        textView.setText(club.getClub_name());
                        Picasso.get().load(club.getImage()).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        infoClubAway.addListenerForSingleValueEvent(clubAway);
    }
}
