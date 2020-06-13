package com.example.mobilesporta.fragment.game;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.activity.game.FootballMatchCreateNew;
import com.example.mobilesporta.activity.game.FootballMatchInfo;
import com.example.mobilesporta.adapter.ItemFootballMatchAdapter;
import com.example.mobilesporta.data.service.ClubService;
import com.example.mobilesporta.data.service.MatchService;
import com.example.mobilesporta.model.ClubModel;
import com.example.mobilesporta.model.MatchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFbMatch_TabFragment extends Fragment {

    MatchService matchService = new MatchService();
    List<MatchModel> listMatch = matchService.getMyListMatch();
    List<String> listMatchId = matchService.getMyListMatchId();

    ClubService clubService = new ClubService();
    Map<String, ClubModel> mapClubs = clubService.getMapClubs();


    Button btnCreateNewMatch;
    ListView lvMatch;

    public MyFbMatch_TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_my_fb_match__tab, container, false);
        connectView(view);

        showMyListMatch();

        return view;
    }

    private void connectView(View view) {
        lvMatch = (ListView) view.findViewById(R.id.lvMy_Fb_Match_Fragment_ListMatch);
    }

    private void showMyListMatch() {
        ItemFootballMatchAdapter itemFootballMatchAdapter = new ItemFootballMatchAdapter(getActivity(), listMatch, mapClubs);
        lvMatch.setAdapter(itemFootballMatchAdapter);
        lvMatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FootballMatchInfo.class);
                intent.putExtra("match_id", listMatchId.get(position));
                startActivity(intent);
            }
        });
    }
}
