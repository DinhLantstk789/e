package com.example.mobilesporta.activity.game;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobilesporta.adapter.ItemStadiumForMatchAdapter;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.StadiumCommentModel;
import com.example.mobilesporta.model.StadiumModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobilesporta.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchSelectStadiumForMatch extends AppCompatActivity {

    EditText edtSearch;
    Button btnCancel;
    ListView lvStadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_select_stadium_for_match);

        map();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSelectStadiumForMatch.this, FootballMatchCreateNew.class);
                startActivity(intent);
            }
        });

        renderItemStadium();
        search();

    }

    private void map(){
        edtSearch = findViewById(R.id.edt_search_stadium_for_match);
        btnCancel = findViewById(R.id.btn_cancel_search_stadium_for_match);
        lvStadium = findViewById(R.id.lv_stadium_for_match);
    }

    private void renderItemStadium(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("stadiums");
        databaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    final ArrayList<StadiumModel> listStadium = new ArrayList<>();
                    final ArrayList<String> listIdStadium = new ArrayList<>();

                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        StadiumModel stadium = snapshot.getValue(StadiumModel.class);
                        listStadium.add(stadium);
                        listIdStadium.add(snapshot.getKey());
                    }

                    ItemStadiumForMatchAdapter itemStadiumForMatchAdapter = new ItemStadiumForMatchAdapter(SearchSelectStadiumForMatch.this, R.layout.item_stadium_for_match, listStadium);
                    lvStadium.setAdapter(itemStadiumForMatchAdapter);
                    lvStadium.setMinimumHeight(500);
                    itemStadiumForMatchAdapter.notifyDataSetChanged();

                    lvStadium.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.putExtra("stadium_id", listIdStadium.get(position));
                            intent.putExtra("stadium_name", listStadium.get(position).getStadium_name());
                            intent.putExtra("stadium_address", listStadium.get(position).getAddress());
                            Log.d("address",  listStadium.get(position).getAddress());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void search(){
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, int count, int after) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("stadiums");
                databaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            final ArrayList<StadiumModel> listStadium = new ArrayList<>();
                            final ArrayList<String> listIdStadium = new ArrayList<>();

                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                StadiumModel stadium = snapshot.getValue(StadiumModel.class);
                                
                                if (stadium.getStadium_name().toLowerCase().contains(s) ||
                                stadium.getZone().toLowerCase().contains(s)){
                                    listIdStadium.add(snapshot.getKey());
                                    listStadium.add(stadium);
                                }
                            }

                            ItemStadiumForMatchAdapter itemStadiumForMatchAdapter = new ItemStadiumForMatchAdapter(SearchSelectStadiumForMatch.this, R.layout.item_stadium_for_match, listStadium);
                            lvStadium.setAdapter(itemStadiumForMatchAdapter);
                            lvStadium.setMinimumHeight(500);
                            itemStadiumForMatchAdapter.notifyDataSetChanged();

                            lvStadium.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.putExtra("stadium_id", listIdStadium.get(position));
                                    intent.putExtra("stadium_name", listStadium.get(position).getStadium_name());
                                    intent.putExtra("stadium_address", listStadium.get(position).getAddress());
                                    Log.d("address",  listStadium.get(position).getAddress());
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
