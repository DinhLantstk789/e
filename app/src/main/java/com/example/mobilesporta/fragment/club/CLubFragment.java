package com.example.mobilesporta.fragment.club;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.club.ClubProfile;
import com.example.mobilesporta.activity.club.ClubSearching;
import com.example.mobilesporta.adapter.ItemClubAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.model.ClubCommentModel;
import com.example.mobilesporta.model.ClubModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLubFragment extends Fragment {

    FloatingActionButton fabAddNew;
    Button btnSearchClub, btnAddNewClub;
    ClubService clubService = new ClubService();
    EditText edtNameClub, edtSlogan, edtDescription;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<ClubModel> listClub = new ArrayList<>();
    ArrayList<String> listClubId = new ArrayList<>();
    ListView lvClub;
    ItemClubAdapter itemClubAdapter;
    TextView none;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_club, container, false);

        fabAddNew = (FloatingActionButton) view.findViewById(R.id.fabClubAdd_AddNewClub);
        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        btnSearchClub = view.findViewById(R.id.btn_search_club);
        lvClub = view.findViewById(R.id.lvClubAct_MyClub);
        none = view.findViewById(R.id.txtClub_NoData);
        fabAddNew = view.findViewById(R.id.fabClubAdd_AddNewClub);
        btnSearchClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ClubSearching.class));
            }
        });

        showListClub();
        return view;
    }

    public void openDialog() {
        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_add_new_club);
        dialog.show();
        edtNameClub = dialog.findViewById(R.id.edtDialogAddNewClub_ClubName);
        edtSlogan = dialog.findViewById(R.id.edtDialogAddNewClub_ClubSlogan);
        edtDescription = dialog.findViewById(R.id.tvDialogAddNewClub_ClubDescription);
        btnAddNewClub = dialog.findViewById(R.id.btnDialogAddNewClub_AddNew);

        btnAddNewClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewClub();
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void addNewClub(){
//        ClubCommentModel clubCommentModel = new ClubCommentModel("", "");
        ArrayList<ClubCommentModel> listComment = new ArrayList<>();
        ClubModel clubModel = new ClubModel();

        String nameClub = edtNameClub.getText().toString();
        String slogan = edtSlogan.getText().toString();
        String description = edtDescription.getText().toString();
        String userId = user.getUid();

        ClubCommentModel clubComment = new ClubCommentModel();
        listComment.add(clubComment);
        clubModel.setListComments(listComment);
        clubModel.setClub_name(nameClub);
        clubModel.setDescription(description);
        clubModel.setSlogan(slogan);
        clubModel.setImage("https://firebasestorage.googleapis.com/v0/b/mobilesporta-5bb33.appspot.com/o/image_club%2Fimage_club.jpg?alt=media&token=7e5b5eb4-7c55-486b-9839-528804ba8f84");
        clubModel.setBackground("https://firebasestorage.googleapis.com/v0/b/mobilesporta-5bb33.appspot.com/o/image_club%2Fbg_club.jpg?alt=media&token=cd844456-84eb-4716-b457-e383868b7871");
        clubModel.setUser_created_id(userId);

        if (nameClub.length() == 0 || slogan.length() == 0 || description.length() == 0){
            Toast.makeText(getActivity(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else{
            clubService.addClub(clubModel);
            Intent intent = new Intent(getActivity(), Home.class);
            intent.putExtra("addclub", "true");
            startActivity(intent);
        }
    }

    private void showListClub(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        Query infoClub = mDatabase.orderByChild("user_created_id").equalTo(user.getUid());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ClubModel clubModel = snapshot.getValue(ClubModel.class);
                        listClub.add(clubModel);
                        listClubId.add(snapshot.getKey());
                    }
                    itemClubAdapter = new ItemClubAdapter(getActivity(), R.layout.item_club, listClub);
                    lvClub.setAdapter(itemClubAdapter);

                    lvClub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), ClubProfile.class);
                            intent.putExtra("club_id", listClubId.get(position));
                            intent.putExtra("user_id", listClub.get(position).getUser_created_id());
                            startActivity(intent);
                        }
                    });
                    none.setVisibility(View.INVISIBLE);
                }else{
                    none.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        infoClub.addListenerForSingleValueEvent(valueEventListener);


    }
}
