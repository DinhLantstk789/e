package com.example.mobilesporta;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mobilesporta.activity.club.ClubProfile;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 1;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Button fakeLoginButton, loginLocalButton, dangky, fakeSigninButton;
    private EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            intent.putExtra("main", "home");
            startActivity(intent);
        }

        LoginManager.getInstance().logOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findId();
        mAuth = FirebaseAuth.getInstance();

        clickDangky();

        //login local
        loginLocalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLocal();
            }
        });

        fakeLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        //login facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        loginFacebook();

        //sign in google
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signinGoogle();
    }

    private void findId() {
        loginButton = findViewById(R.id.login_button); // button facebook login
        signInButton = findViewById(R.id.sign_in_button); // dang nhap google
        loginLocalButton = findViewById(R.id.loginLocal); // dn tai khoan local
        dangky = findViewById(R.id.register); // nut dang ky
        fakeLoginButton = findViewById(R.id.fake_login_button); // nut gia login facebook
        fakeSigninButton = findViewById(R.id.fake_signin_button);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);
    }

    private void loginFacebook() {
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void signinGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        fakeSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fake_signin_button:
                        signIn();
                        break;
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.d("Error", e.toString());
            }
        }

    }

    private void clickDangky() {
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewEdit = getLayoutInflater().inflate(R.layout.dialog_register, null);
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(viewEdit);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                Button btnRegister = dialog.findViewById(R.id.btnRegister);
                final EditText edtEmail = dialog.findViewById(R.id.register_email);
                final EditText edtPass = dialog.findViewById(R.id.register_pass);
                final EditText edtrePass = dialog.findViewById(R.id.register_re_pass);
                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        register(edtEmail, edtPass, edtrePass, dialog);
                    }
                });
            }
        });
    }

    private void loginLocal() {

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            if (password.length() < 6) {
                Toast.makeText(MainActivity.this, "Mật khẩu phải lớn hơn 6 ký tự", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    intent.putExtra("main", "home");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("log", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            intent.putExtra("main", "home");
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(MainActivity.this, Home.class);
                            intent.putExtra("main", "home");
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void register(EditText edtEmail, EditText edtPass, EditText edtrePass, final Dialog dialog){
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        String rePassword = edtrePass.getText().toString();
        if (email.length() == 0 || password.length() == 0 || rePassword.length() == 0){
            Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
        }else{
            if (password.equals(rePassword) == false){
                Toast.makeText(MainActivity.this, "Nhập lại mật khẩu không đúng", Toast.LENGTH_LONG).show();
            }else{
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                                    FirebaseAuth.getInstance().signOut();
                                    dialog.hide();
                                } else {
                                    Toast.makeText(MainActivity.this, "Email không hợp lệ hoặc mật khẩu lớn hơn 6 ký tự", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
    }
}
