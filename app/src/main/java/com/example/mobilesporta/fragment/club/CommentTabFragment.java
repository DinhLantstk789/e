package com.example.mobilesporta.fragment.club;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;
import com.example.mobilesporta.adapter.ItemCommentClubAdapter;
import com.example.mobilesporta.data.service.ClubCommentService;
import com.example.mobilesporta.model.ClubCommentModel;
import com.example.mobilesporta.model.ClubModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class CommentTabFragment extends Fragment {

    ListView lvComment;
    ImageButton imgBtnAddComment;
    private EditText edtComment;
    private String clubId;
    private FirebaseUser user;
    private String userId;
    private LinearLayout llGroupComment;

    public CommentTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_comment_tab, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();

        lvComment = view.findViewById(R.id.lv_comment_club);
        imgBtnAddComment = view.findViewById(R.id.btn_enter_comment);
        edtComment = view.findViewById(R.id.edt_comment_club);
        llGroupComment = view.findViewById(R.id.group_comment);

        clubId = getArguments().getString("club_id");
        userId = getArguments().getString("user_id");


        if (user.getUid().equals(userId)){
            llGroupComment.setVisibility(View.GONE);
        }
        renderClubComment(clubId, user.getUid());

        imgBtnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClubCommentModel clubComment = new ClubCommentModel();
                clubComment.setUser_id(user.getUid());
                clubComment.setContent(edtComment.getText().toString());
                clubComment.setDate(LocalDate.now().toString());

                String name;
                if (user.getDisplayName() == null){
                    name = user.getEmail();
                }else{
                    name = user.getDisplayName();
                }
                clubComment.setUser_name(name);

                String avatar;
                if (user.getPhotoUrl() == null){
                    avatar = "https://firebasestorage.googleapis.com/v0/b/mobilesporta-5bb33.appspot.com/o/image_account%2Fphoto.jpg?alt=media&token=af122689-ff5f-4435-80ad-0304efef367d";
                }else{
                    avatar = user.getPhotoUrl().toString();
                }
                clubComment.setAvatar(avatar);

                ClubCommentService clubCommentService = new ClubCommentService();
                clubCommentService.addClubComment(clubId, clubComment);

                edtComment.setText("");
            }
        });

        return view;
    }

    public void renderClubComment(final String clubId, final String userId){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("clubs").child(clubId).child("listComment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ArrayList<ClubCommentModel> listClubComment = new ArrayList<>();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        ClubCommentModel clubComment = snapshot.getValue(ClubCommentModel.class);
                        if (clubComment.getUser_id().equals(userId) == true){
                            llGroupComment.setVisibility(View.GONE);
                        }
                        listClubComment.add(clubComment);
                    }
                    ItemCommentClubAdapter itemCommentClubAdapter = new ItemCommentClubAdapter(getActivity(), listClubComment);
                    lvComment.setAdapter(itemCommentClubAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
