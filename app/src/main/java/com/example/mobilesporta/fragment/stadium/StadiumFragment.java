package com.example.mobilesporta.fragment.stadium;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.StadiumModel;

import java.util.ArrayList;

public class StadiumFragment extends Fragment {

    ListView stadiumList;
    SearchView searchList;
    ArrayList<StadiumModel> arrayStadium;
    StadiumAdapter stadiumAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stadium, container, false);

        stadiumList = view.findViewById(R.id.listStadium);
        arrayStadium = new ArrayList<StadiumModel>();
        arrayStadium.add(new StadiumModel("Sân bóng 1","Hà Nội","6.am","","","","","","","",""));
        arrayStadium.add(new StadiumModel("Sân bóng 2","Hà Nội","6.am","","","","","","","",""));
        arrayStadium.add(new StadiumModel("Sân bóng 3","Hà Nội","6.am","","","","","","","",""));
        stadiumAdapter = new StadiumAdapter(
                getActivity(),R.layout.list_stadium_view,arrayStadium
        );
        stadiumList.setAdapter(stadiumAdapter);
        stadiumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(),StadiumView.class);
                i.putExtra("title",arrayStadium.get(position).getStadium_name());
                startActivity(i);
            }
        });

        searchList = view.findViewById(R.id.searchStadium);
        searchList.setQueryHint("Tìm kiếm...");
        searchList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String Text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String Text) {
                stadiumAdapter.getFilter().filter(Text);
                return false;
            }
        });
        return view;
    }


//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.stadium_menu, menu);
//        MenuItem item = menu.findItem(R.id.searchStadium);
//        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                stadiumAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
//    }
}
