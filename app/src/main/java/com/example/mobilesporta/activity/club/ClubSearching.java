package com.example.mobilesporta.activity.club;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;
import com.example.mobilesporta.adapter.ItemClubAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.model.ClubModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.mobilesporta.R.*;

public class ClubSearching extends AppCompatActivity {

    ListView searchClub;
    Button btnCancel;
    EditText edtSearch;
    ArrayList<ClubModel> listClub;
    ArrayList<String> listClubId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_club_searching);

        searchClub = findViewById(id.lvClubSearchingAct_SearchClub);
        btnCancel = findViewById(id.btn_cancel_search);
        edtSearch = findViewById(id.edt_search_club);
        searchClub = findViewById(R.id.lvClubSearchingAct_SearchClub);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubSearching.this, Home.class);
                intent.putExtra("addclub", "true");
                startActivity(intent);
            }
        });

        search();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchview_club, menu);
        MenuItem item = menu.findItem(R.id.searchClub_Item);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void search(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query search = mDatabase;

        search.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    listClub = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        if (user.getUid().equals(clubModel.getUser_created_id()) == false){
                            listClub.add(clubModel);
                            listClubId.add(snapshot.getKey());
                        }
                    }

                    ItemClubAdapter itemClubAdapter = new ItemClubAdapter(ClubSearching.this, layout.item_club, listClub);
                    searchClub.setAdapter(itemClubAdapter);

                    searchClub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ClubSearching.this, ClubProfile.class);
                            intent.putExtra("club_id", listClubId.get(position));
                            intent.putExtra("user_id", listClub.get(position).getUser_created_id());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, final int start, int before, int count) {
                    ArrayList<ClubModel> listClubs = new ArrayList<>();
                    final ArrayList<String> listClubIds = new ArrayList<>();
                    for (int i = 0 ; i < listClub.size() ; i++){
                        if (listClub.get(i).getClub_name().toLowerCase().contains(s)){
                            listClubs.add(listClub.get(i));
                            listClubIds.add(listClubId.get(i));
                        }
                    }
                    ItemClubAdapter itemClubAdapter = new ItemClubAdapter(ClubSearching.this, layout.item_club, listClubs);
                    searchClub.setAdapter(itemClubAdapter);

                    searchClub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ClubSearching.this, ClubProfile.class);
                            intent.putExtra("club_id", listClubIds.get(position));
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

    }
}
