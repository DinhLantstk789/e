package com.example.mobilesporta.data.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobilesporta.model.MatchModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

import com.example.mobilesporta.model.MatchModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class MatchService {
    List<MatchModel> listMatchModels = new ArrayList<>();
    List<String> listMatchId = new ArrayList<>();
    List<String> listMatchIdByDate = new ArrayList<>();
    Map<String, MatchModel> mapMatchs = new HashMap<>();

    Map<String, MatchModel> mapMatchByDateTime = new HashMap<>();
    List<MatchModel> listMatchByDate = new ArrayList<>();

    String matchKey = new String();

    public void addMatch(MatchModel matchModel){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("matchs").push().setValue(matchModel);
    }

    public List<MatchModel> getListMatchByDateTime(final String date) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchsByDate = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    listMatchByDate.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);
                        try {

                            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(match.getDate());

                            if(date1.compareTo(date2) <= 0) {
                                listMatchByDate.add(match);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e("e : ", e.toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchsByDate.addListenerForSingleValueEvent(valueEventListener);

        return listMatchByDate;
    }

    public Map<String, MatchModel> getMapMatchByDateTime(final String date) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    mapMatchByDateTime.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);
                        try {

                            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(match.getDate());

                            if(date1.compareTo(date2) <= 0) {
                                mapMatchByDateTime.put(snapshot.getKey(), match);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e("e : ", e.toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return mapMatchByDateTime;
    }

    public List<MatchModel> getListMatch() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);

                        listMatchModels.add(match);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return listMatchModels;
    }

    public List<MatchModel> getMyListMatch() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String user_id = user.getUid();
                Log.e("us_id", user_id);

                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);
                        if (match.getUser_created_id().equals(user_id)) {

                            listMatchModels.add(match);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return listMatchModels;
    }

    public List<String> getListMatchId() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        listMatchId.add(snapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return listMatchId;
    }

    public List<String> getListMatchIdByDate(final String date) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query myMatchIds = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    listMatchIdByDate.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);
                        try {

                            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(match.getDate());

                            if(date1.compareTo(date2) <= 0) {
                                listMatchIdByDate.add(snapshot.getKey());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e("e : ", e.toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        myMatchIds.addListenerForSingleValueEvent(valueEventListener);

        return listMatchIdByDate;
    }

    public List<String> getMyListMatchId() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query matchs = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String user_id = user.getUid();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MatchModel match = snapshot.getValue(MatchModel.class);
                        if (match.getUser_created_id().equals(user_id)) {

                            listMatchId.add(snapshot.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        matchs.addListenerForSingleValueEvent(valueEventListener);

        return listMatchId;
    }

    public Map<String, MatchModel> getMapMatchs() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query a = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        MatchModel matchModel = snapshot.getValue(MatchModel.class);
                        mapMatchs.put(snapshot.getKey(), matchModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        a.addListenerForSingleValueEvent(valueEventListener);

        return mapMatchs;
    }

    public String getMatchKeyFromObject(final MatchModel matchModel) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs");
        Query a = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        MatchModel match = snapshot.getValue(MatchModel.class);
                        Log.e("1", matchModel.toString());
                        Log.e("112", match.toString());
                        if (match.equals(matchModel)) {
                            matchKey = snapshot.getKey();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        a.addListenerForSingleValueEvent(valueEventListener);

        return matchKey;
    }

    public void deleteMatch(String match_id){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("matchs").child(match_id);
        mDatabase.removeValue();
    }
}
