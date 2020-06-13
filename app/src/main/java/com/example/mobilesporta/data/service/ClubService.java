package com.example.mobilesporta.data.service;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.club.ClubProfile;
import com.example.mobilesporta.activity.club.ClubSearching;
import com.example.mobilesporta.adapter.ItemClubAdapter;
import com.example.mobilesporta.model.ClubModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubService {
    ArrayList<ClubModel> listClub = new ArrayList<>();
    Map<String, ClubModel> mapClubs = new HashMap<>();
    private String description;

    public void addClub(ClubModel clubModel){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("clubs").push().setValue(clubModel);
    }

    public void getDataClub(String clubId, final TextView txtName, final TextView txtSlogan, final ImageView avatar, final ImageView background) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query infoClub = mDatabase.orderByKey().equalTo(clubId);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClubModel club = snapshot.getValue(ClubModel.class);
                        txtName.setText(club.getClub_name());
                        txtSlogan.setText(club.getSlogan());
                        Picasso.get().load(club.getImage()).into(avatar);
                        Picasso.get().load(club.getBackground()).into(background);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        infoClub.addListenerForSingleValueEvent(valueEventListener);
    }

    public void updateAvatarCLub(String clubId, final String uri){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query updateAvatar = mDatabase.child(clubId);

        updateAvatar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("image").setValue(uri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateBackground(String clubId, final String uri){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query updateAvatar = mDatabase.child(clubId);

        updateAvatar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("background").setValue(uri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void renderDescription(String clubId, final TextView textView){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query infoClub = mDatabase.orderByKey().equalTo(clubId);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        description = clubModel.getDescription();
                        textView.setText(description);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        infoClub.addListenerForSingleValueEvent(valueEventListener);
    }

    public void deleteClub(String clubId){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs").child(clubId);
        mDatabase.removeValue();
    }

    public void updateDescriptionClub(String clubId, final String des){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query updateDescription = mDatabase.child(clubId);

        updateDescription.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("description").setValue(des);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void  updateSloganClub(String clubId, final String slogan){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query updateDescription = mDatabase.child(clubId);

        updateDescription.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("slogan").setValue(slogan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateClubName(String clubId, final String clubName){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query updateDescription = mDatabase.child(clubId);

        updateDescription.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("club_name").setValue(clubName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void renderDialogUpdateClub(String clubId, final EditText edtName, final EditText slogan, final EditText description){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query infoClub = mDatabase.orderByKey().equalTo(clubId);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        edtName.setText(clubModel.getClub_name());
                        slogan.setText(clubModel.getSlogan());
                        description.setText(clubModel.getDescription());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        infoClub.addListenerForSingleValueEvent(valueEventListener);
    }

    public List<ClubModel> getListClubs() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query a = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        listClub.add(clubModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        a.addListenerForSingleValueEvent(valueEventListener);

        return listClub;
    }

    public List<ClubModel> getClubById(final String clubId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query a = mDatabase.orderByKey().equalTo(clubId);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        listClub.add(clubModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        a.addListenerForSingleValueEvent(valueEventListener);

        return listClub;
    }

    public Map<String, ClubModel> getMapClubs() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query a = mDatabase.orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        mapClubs.put(snapshot.getKey(), clubModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        a.addListenerForSingleValueEvent(valueEventListener);

        return mapClubs;
    }

}
