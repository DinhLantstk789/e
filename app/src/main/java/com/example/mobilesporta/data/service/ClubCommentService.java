package com.example.mobilesporta.data.service;

import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.mobilesporta.model.ClubCommentModel;
import com.example.mobilesporta.model.ClubModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClubCommentService {

    ArrayList<ClubCommentModel> listComment = new ArrayList<>();

    public ClubCommentService() {
    }

    public void addClubComment(String clubId, ClubCommentModel clubCommentModel) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("clubs").child(clubId).child("listComment").push().setValue(clubCommentModel);
    }

}
