package com.example.mobilesporta.activity.game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilesporta.Home;
import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.club.ClubProfile;
import com.example.mobilesporta.data.MapConst;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;
import com.example.mobilesporta.model.StadiumModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;
import java.util.logging.SimpleFormatter;

public class FootballMatchCreateNew extends AppCompatActivity {

    TextView edtStadium;
    TextView edtTimeAmount;
    TextView edtDate;
    TextView edtTime;
    EditText edtPhoneNumber;
    EditText edtDescription;
    Button btnSave;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Button btnBack;
    Spinner spSelectClub;
    String clubId;
    String idStadium, stadiumAddress;

    public static final int ACTIVITYB_REQUEST = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_match_create_new);

        connectView();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        edtDate.setText(date.format(calendar.getTime()));
        edtTime.setText(time.format(calendar.getTime()));
        edtTimeAmount.setText("90");
        spSelectClub.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballMatchCreateNew.this, Home.class);
                intent.putExtra("add_match", "true");
                startActivity(intent);
            }
        });

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
            }
        });

        edtTimeAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNumberPickerDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                createMatch();
            }
        });

        //select club
        final ArrayList<ClubModel> listMyClub = new ArrayList<>();
        final ArrayList<String> listNameClub = new ArrayList<>();
        final ArrayList<String> listClubId = new ArrayList<>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("clubs");
        mDatabase.orderByChild("user_created_id").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        ClubModel club = snapshot.getValue(ClubModel.class);
                        listMyClub.add(club);
                        listNameClub.add(club.getClub_name());
                        listClubId.add(snapshot.getKey());
                    }
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(FootballMatchCreateNew.this, android.R.layout.simple_spinner_item, listNameClub);
                spSelectClub.setAdapter(arrayAdapter);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spSelectClub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        clubId = listClubId.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edtStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballMatchCreateNew.this, SearchSelectStadiumForMatch.class);
                startActivityForResult(intent, ACTIVITYB_REQUEST);;
            }
        });
    }

    private void connectView() {
        edtStadium = findViewById(R.id.edtMatchCreateAct_Stadium);
        edtTimeAmount = findViewById(R.id.edtMatchCreateAct_TimeAmount);
        edtDate = findViewById(R.id.edtMatchCreateAct_Date);
        edtTime = findViewById(R.id.edtMatchCreateAct_Time);
        edtPhoneNumber = findViewById(R.id.edtMatchCreateAct_PhoneNumber);
        edtDescription = findViewById(R.id.edtMatchCreateAct_Description);
        btnSave = findViewById(R.id.btnMatchCreateAct_Save);
        spSelectClub = findViewById(R.id.tv_select_club);
        btnBack = findViewById(R.id.btn_back_create_match);
    }

    private void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                edtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void selectTime(){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, minute);
                edtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, hour, min, true);

        timePickerDialog.show();
    }

    private void createMatch(){
        if (edtStadium.getText().toString().equals("Chọn sân")){
            showToast("Hãy chọn sân bóng");
        }else if (edtDate.getText().toString().equals("")
        || edtTime.getText().toString().equals("") || edtTimeAmount.getText().toString().equals("")
        || edtPhoneNumber.getText().toString().equals("") || edtDescription.getText().toString().equals("")){
            showToast("Nhập đầy đủ thông tin");
        }
        else{

            String user_created_id = user.getUid();
            String club_home_id = clubId;
            String club_away_id = "";
            String stadium_id = idStadium;
            String date = edtDate.getText().toString();
            String time = edtTime.getText().toString();
            String time_amount = edtTimeAmount.getText().toString();
          
            final MapConst mapConst = new MapConst();
            String status = mapConst.STATUS_MATCH_MAP.get("NONE");
          
            String description = edtDescription.getText().toString();
            String phone_number = edtPhoneNumber.getText().toString();
            MatchModel matchModel = new MatchModel(club_home_id, club_away_id, user_created_id, stadium_id, date, time, time_amount, status, description, phone_number);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("matchs").push().setValue(matchModel, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    Intent intent = new Intent(FootballMatchCreateNew.this, FootballMatchInfo.class);
                    intent.putExtra("match_id", databaseReference.getKey());
                    intent.putExtra("stadium_name", edtStadium.getText().toString());
                    intent.putExtra("stadium_address", stadiumAddress);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    private void openNumberPickerDialog(){
        View v1 = getLayoutInflater().inflate(R.layout.dialog_number_picker, null);

        final NumberPicker picker = (NumberPicker) v1.findViewById(R.id.np_time_amout);
        String nums[] = {"60", "90", "120"};
        picker.setMinValue(0);
        picker.setMaxValue(nums.length - 1);
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(nums);

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setView (v1);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int pickedValue = picker.getValue();
                edtTimeAmount.setText(60 + pickedValue * 30 + "");
                return;
            } });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String newString = data.getStringExtra("stadium_name");
                    edtStadium.setText(newString);
                    idStadium = data.getStringExtra("stadium_id");
                    stadiumAddress = data.getStringExtra("stadium_address");
                }
            }
        }
    }

    public void showToast(String message){
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
}
