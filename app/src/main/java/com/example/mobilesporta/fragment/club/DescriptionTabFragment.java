package com.example.mobilesporta.fragment.club;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.model.ClubModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionTabFragment extends Fragment {

    TextView tvDescription;

    public DescriptionTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_description_tab, container, false);
        tvDescription = view.findViewById(R.id.textView_description);
        String clubId = getArguments().getString("club_id");

        ClubService clubService = new ClubService();
        clubService.renderDescription(clubId, tvDescription);

        return view;
    }
}
