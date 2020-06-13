package com.example.mobilesporta.activity.club;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;
import com.example.mobilesporta.adapter.PageAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.fragment.club.DescriptionTabFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ClubProfile extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem commentTab, historyTab, descriptionTab;
    public PagerAdapter pagerAdapter;
    private String clubId;
    private ClubService clubService;
    private ImageView imgClub;
    private TextView txtNameClub, txtSlogan;
    private Button btnAddAvatar, btnAddBackground;
    final int IMAGE_PICK_CODE = 1, RESULT_OK = -1, BACKGROUND_PICK_CODE = 2;
    Uri imageURI, bgURI;
    private StorageReference storageRef;
    ImageView bgClub;
    private String userId;
    private Button btnOptionClub, btnEditInfoClub, btnDeleteClub, btnBack, btnUpdateClub;
    private EditText edtClubName, edtSlogan, edtDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_club_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent idClubFromFragment = getIntent();
        clubId = idClubFromFragment.getStringExtra("club_id");
        userId = idClubFromFragment.getStringExtra("user_id");

        map();

        btnOptionClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        updateUI();

        if (user.getUid().equals(userId) == true){
            addAvatar();
            addBackround();
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ClubProfile.this, Home.class);
                    intent.putExtra("addclub", "true");
                    startActivity(intent);
                }
            });
        }else{
            btnAddBackground.setVisibility(View.GONE);
            btnAddAvatar.setVisibility(View.GONE);
            btnOptionClub.setVisibility(View.GONE);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ClubProfile.this, ClubSearching.class);
                    startActivity(intent);
                }
            });
        }

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), clubId, userId);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void map(){
        tabLayout = (TabLayout) findViewById(R.id.ClubProfieAct_TabLayout);
        commentTab = (TabItem) findViewById(R.id.ClubProfieAct_ClubCommentTab);
        historyTab = (TabItem) findViewById(R.id.ClubProfieAct_ClubHistoryTab);
        descriptionTab = (TabItem) findViewById(R.id.ClubProfieAct_ClubDescriptionTab);
        imgClub = findViewById(R.id.imgClubProfileAct_ClubImage);
        txtNameClub = findViewById(R.id.txtClubProfile_ClubName);
        txtSlogan = findViewById(R.id.txtClubProfile_ClubSlogan);
        viewPager = findViewById(R.id.ClubProfieAct_ViewPager);
        btnAddAvatar = findViewById(R.id.btn_add_avatar);
        bgClub = findViewById(R.id.imgClubProfileAct_ClubBackground);
        btnAddBackground = findViewById(R.id.btn_add_bachground);
        btnOptionClub = findViewById(R.id.btn_select_option_club);
        btnBack = findViewById(R.id.btn_back);
    }

    private void openDialog(){
        View view = getLayoutInflater().inflate(R.layout.dialog_option_club, null);
        final Dialog dialog = new BottomSheetDialog(ClubProfile.this);
        dialog.setContentView(view);
        dialog.show();

        btnEditInfoClub = dialog.findViewById(R.id.btn_edit_info_club);
        btnDeleteClub = dialog.findViewById(R.id.btn_delete_club);

        btnDeleteClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClubService clubService = new ClubService();
                clubService.deleteClub(clubId);
                Intent intent = new Intent(ClubProfile.this, Home.class);
                intent.putExtra("addclub", "true");
                startActivity(intent);
            }
        });

        btnEditInfoClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdateInfo(dialog);
            }
        });
    }

    private void openDialogUpdateInfo(final Dialog dialog){
        View viewEdit = getLayoutInflater().inflate(R.layout.dialog_update_info_club, null);
        final Dialog dialogEdit = new Dialog(ClubProfile.this);
        dialogEdit.setContentView(viewEdit);
        dialogEdit.show();
        Window window = dialogEdit.getWindow();
        window.setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);

        btnUpdateClub = dialogEdit.findViewById(R.id.btn_dl_update_club);
        edtClubName = dialogEdit.findViewById(R.id.edt_dl_club_name);
        edtSlogan = dialogEdit.findViewById(R.id.edt_dl_slogan);
        edtDescription = dialogEdit.findViewById(R.id.edt_dl_des);

        ClubService clubServies = new ClubService();
        clubServies.renderDialogUpdateClub(clubId, edtClubName, edtSlogan, edtDescription);

        btnUpdateClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClubService clubService = new ClubService();
                clubService.updateClubName(clubId, edtClubName.getText().toString());
                clubService.updateSloganClub(clubId, edtSlogan.getText().toString());
                clubService.updateDescriptionClub(clubId, edtDescription.getText().toString());
                Intent intent = new Intent(ClubProfile.this, Home.class);
                intent.putExtra("addclub", "true");
                intent.putExtra("edit", "true");
                intent.putExtra("club_id", clubId);
                startActivity(intent);
            }
        });
    }

    private void updateUI(){
        clubService = new ClubService();
        clubService.getDataClub(clubId, txtNameClub, txtSlogan, imgClub, bgClub);
    }

    private void addAvatar(){
        btnAddAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_PICK_CODE);
            }
        });
    }


    private void addBackround(){
        btnAddBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, BACKGROUND_PICK_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageURI = data.getData();
            Picasso.get().load(imageURI).into(imgClub);
            updateAvatar(imageURI);
        }

        if (resultCode == RESULT_OK && requestCode == BACKGROUND_PICK_CODE){
            bgURI = data.getData();
            Picasso.get().load(bgURI).into(bgClub);
            updateBackground(bgURI);
        }
    }

    private void updateAvatar(Uri uriImage){

        storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference imageName = storageRef.child("image_club/avatar_uid_" + clubId);
        imageName.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ClubService updateImage = new ClubService();
                        updateImage.updateAvatarCLub(clubId, uri.toString());
                    }
                });
            }
        });

    }

    private void updateBackground(Uri uriImage){
        storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference imageName = storageRef.child("image_club/background_uid_" + clubId);
        imageName.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ClubService updateImage = new ClubService();
                        updateImage.updateBackground(clubId, uri.toString());
                    }
                });
            }
        });
    }
}
