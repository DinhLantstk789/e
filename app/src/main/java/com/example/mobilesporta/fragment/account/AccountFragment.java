package com.example.mobilesporta.fragment.account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.MainActivity;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.account.EditAccount;
import com.example.mobilesporta.activity.account.PravicyPolicy;
import com.example.mobilesporta.activity.account.Recommend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.widget.Toast.LENGTH_SHORT;

public class AccountFragment extends Fragment {

    Button logout, editAccount, recommend, security, btnAddImage;
    TextView username;
    ImageView avatar;
    final int IMAGE_PICK_CODE = 1, RESULT_OK = -1;
    Uri imageURI;
    Uri uriAvatar;
    private StorageReference storageRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);;

        avatar = view.findViewById(R.id.avatar);
        username = view.findViewById(R.id.text_email);
        logout = view.findViewById(R.id.logout);
        editAccount = view.findViewById(R.id.edit_account);
        recommend = view.findViewById(R.id.gt);
        security = view.findViewById(R.id.security_policy);
        btnAddImage = view.findViewById(R.id.add_profile_image);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditAccount.class);
                startActivity(intent);
            }
        });

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Recommend.class);
                startActivity(intent);
            }
        });

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PravicyPolicy.class);
                startActivity(intent);
            }
        });

        return view;

    }

    private void updateUI(FirebaseUser currentUser){

        if (currentUser.getDisplayName() == null || currentUser.getDisplayName().length() == 0){
            username.setText(currentUser.getEmail());
        }else{
            username.setText(currentUser.getDisplayName());
        }

        if (currentUser.getPhotoUrl() == null){
            uriAvatar = Uri.parse("https://firebasestorage.googleapis.com/v0/b/mobilesporta-5bb33.appspot.com/o/image_account%2Fphoto.jpg?alt=media&token=af122689-ff5f-4435-80ad-0304efef367d");
        }else{
            uriAvatar = currentUser.getPhotoUrl();
        }

        Picasso.get().load(uriAvatar).into(avatar);
    }

    private void addImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageURI = data.getData();
            Picasso.get().load(imageURI).into(avatar);
            updateAvatar(imageURI);
        }
    }

    private void updateAvatar(Uri uriImage){

        storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference imageName = storageRef.child("image_account/uid_" + user.getUid());
        imageName.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        }
                                    }
                                });
                    }
                });
            }
        });

    }
}
