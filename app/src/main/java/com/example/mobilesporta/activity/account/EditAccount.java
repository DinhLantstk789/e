package com.example.mobilesporta.activity.account;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesporta.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditAccount extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findId();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        edtEmail.setText(user.getEmail());
        if (user.getDisplayName() != null)
            edtUsername.setText(user.getDisplayName());
        else
            edtUsername.setText(user.getEmail());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void findId(){
        edtUsername = findViewById(R.id.edit_name);
        edtEmail = findViewById(R.id.edit_email);
        edtPassword = findViewById(R.id.edit_pass);
        edtConfirmPassword= findViewById(R.id.edit_confirm_pass);
        btnUpdate = findViewById(R.id.btn_update);
    }

    private void updateAccount(){
        final String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        String username = edtUsername.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (username.equals("")){
            if (user.getDisplayName() == null){
                username = user.getEmail();
            }else{
                username = user.getDisplayName();
            }
        }else{
            username = edtUsername.getText().toString();
        }

        if (password.length() > 0 && confirmPassword.length() > 0){
            if (password.equals(confirmPassword) == false){
                edtPassword.setText("");
                edtConfirmPassword.setText("");
                showToastError("nhập lại mật khẩu không đúng");
            }else{
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                }
                            }
                        });
                user.updatePassword(password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                showToastSuccess("Đổi mật khẩu thành công");
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(EditAccount.this, MainActivity.class));
                            }
                        });
            }
        }else if (password.length() == 0 && confirmPassword.length() == 0){

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(EditAccount.this, Home.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }

    public void showToastError(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.toast_error));

        TextView mes = layout.findViewById(R.id.text_error);
        mes.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
    }

    public void showToastSuccess(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_success, (ViewGroup) findViewById(R.id.toast_success));

        TextView mes = layout.findViewById(R.id.text_success);
        mes.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
    }
}
