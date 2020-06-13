package com.example.mobilesporta.fragment.club;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.game.FootballMatchInfo;
import com.example.mobilesporta.adapter.ItemCommentClubAdapter;
import com.example.mobilesporta.adapter.ItemHistoryClubAdapter;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryTabFragment extends Fragment {

    ListView listView;
    public HistoryTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_tab, container, false);
        listView = view.findViewById(R.id.lv_history);


        String clubId = getArguments().getString("club_id");

        renderHistoryHome(clubId, listView);

        renderHistoryAway(clubId, listView);

        return view;
    }

    private void renderHistoryHome(final String clubId, final ListView listView){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");

        ValueEventListener clubHome = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ArrayList<MatchModel> listMatchByHome = new ArrayList<>();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        MatchModel matchModel = snapshot.getValue(MatchModel.class);
                        if ((matchModel.getClub_home_id().equals(clubId) && matchModel.getClub_away_id().length() != 0)|| matchModel.getClub_away_id().equals(clubId)){
                            listMatchByHome.add(matchModel);
                        }
                    }

                    ItemHistoryClubAdapter itemHistoryClubAdapter = new ItemHistoryClubAdapter(getActivity(), listMatchByHome);
                    listView.setAdapter(itemHistoryClubAdapter);
                    itemHistoryClubAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDatabase.addListenerForSingleValueEvent(clubHome);
    }

    private void renderHistoryAway(String clubId, final ListView listView){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");

        Query query1 = mDatabase.orderByChild("club_away_id").equalTo(clubId);
        ValueEventListener clubHome = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ArrayList<MatchModel> listMatchByHome = new ArrayList<>();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        MatchModel matchModel = snapshot.getValue(MatchModel.class);
                        listMatchByHome.add(matchModel);
                    }

                    ItemHistoryClubAdapter itemHistoryClubAdapter = new ItemHistoryClubAdapter(getActivity(), listMatchByHome);
                    listView.setAdapter(itemHistoryClubAdapter);
                    itemHistoryClubAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        query1.addListenerForSingleValueEvent(clubHome);
    }
}
