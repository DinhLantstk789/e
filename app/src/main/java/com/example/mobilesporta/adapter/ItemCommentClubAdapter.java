package com.example.mobilesporta.adapter;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.mobilesporta.R;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.model.ClubCommentModel;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ItemCommentClubAdapter extends BaseAdapter {
    private Activity activity;
    private List<ClubCommentModel> listClubComment;

    public ItemCommentClubAdapter(Activity activity, List<ClubCommentModel> listClubComment) {
        this.activity = activity;
        this.listClubComment = listClubComment;
    }

    @Override
    public int getCount() {
        return listClubComment.size();
    }

    @Override
    public Object getItem(int position) {
        return listClubComment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_comment_club, null);

        TextView tvNameUser = convertView.findViewById(R.id.tv_name_user);
        TextView tvTime = convertView.findViewById(R.id.tv_time);
        TextView tvComment = convertView.findViewById(R.id.tv_comment_club);
        ImageView imgAvatar = convertView.findViewById(R.id.img_avatar);

        int size = listClubComment.size();
        tvTime.setText(listClubComment.get(size - 1 - position).getDate());
        tvComment.setText(listClubComment.get(size - 1 - position).getContent());
        tvNameUser.setText(listClubComment.get(size - 1 - position).getUser_name());

        renderImage(listClubComment.get(size - 1 - position).getUser_id(), imgAvatar);

        return convertView;
    }

    private void renderImage(String userId, final ImageView imageView){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("image_account").child("uid_" + userId);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/mobilesporta-5bb33.appspot.com/o/image_account%2Fphoto.jpg?alt=media&token=af122689-ff5f-4435-80ad-0304efef367d").into(imageView);
            }
        });
    }
}
