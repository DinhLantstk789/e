package com.example.mobilesporta.fragment.game;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.game.FootballMatchInfo;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SugesstionsFbMatch_TabFragment extends Fragment {

    MatchService matchService = new MatchService();
    List<MatchModel> listMatch = matchService.getListMatch();
    List<String> listMatchId = matchService.getListMatchId();
    List<String> listMatchIdByDate = new ArrayList<>();

    List<MatchModel> listMatchByDate = new ArrayList<>();
    Map<String, MatchModel> mapMatchModelsByDate = new HashMap<>();

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();

    ListView listView;

    TextView txtDate;
    TextView txtTime;

    ItemFootballMatchAdapter itemFootballMatchAdapter;

    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat sDF = new SimpleDateFormat("dd/MM/yyy");

    public SugesstionsFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sugesstions_fb_match__tab, container, false);
        connectView(view);

        pickTime();
        pickDate();
        showListMatch();
        // Inflate the layout for this fragment
        return view;
    }

    private void connectView(View view) {
        listView = (ListView) view.findViewById(R.id.lvSugesstion_Match_Fragment_ListMatch);
        txtTime = (TextView) view.findViewById(R.id.txtSugesstion_Match_Fragment_FilterTimeSearch);
        txtDate = (TextView) view.findViewById(R.id.txtSugesstion_Match_Fragment_FilterDateSearch);
    }

    private void showListMatch() {

        mapMatchModelsByDate = matchService.getMapMatchByDateTime(txtDate.getText().toString());
        listMatchByDate = matchService.getListMatchByDateTime(txtDate.getText().toString());
        listMatchIdByDate = matchService.getListMatchIdByDate(txtDate.getText().toString());

        Log.e("msg1", String.valueOf(mapMatchModelsByDate.size()));
        Log.e("msg2", String.valueOf(listMatchByDate.size()));

        itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatchByDate, mapClubs);
        listView.setAdapter(itemFootballMatchAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FootballMatchInfo.class);
                intent.putExtra("match_id", listMatchIdByDate.get(position));
                startActivity(intent);
            }
        });
    }


//    private String getMatchKeyFromObject(MatchModel match) {
//        String matchKey = new String();
//        for (Map.Entry<String, MatchModel> entry : mapMatchModelsByDate.entrySet()) {
//            Log.e("1", match.toString());
//            Log.e("12", entry.getValue().toString());
//            if(entry.getValue().equals(match)) {
//                Log.e("1", "0");
//
//                matchKey = entry.getKey();
//                Log.e("key", matchKey);
//            }
//        }
//        return  matchKey;
//    }

    private void pickTime() {
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
    }

    private void pickDate() {
        txtDate.setText(sDF.format(calendar.getTime()));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        txtDate.setText(sDF.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
    }

}
