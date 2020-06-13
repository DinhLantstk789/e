package com.example.mobilesporta.fragment.stadium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesporta.R;
import com.example.mobilesporta.model.StadiumModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StadiumAdapter extends BaseAdapter implements Filterable {

    Context myContext;
    int myLayout;
    List<StadiumModel> arrayStadium;
    List<StadiumModel> arrayStadiumAll;

    public StadiumAdapter(Context context, int layout, List<StadiumModel> stadiumlist){
        myContext = context;
        myLayout = layout;
        arrayStadium = stadiumlist;
        this.arrayStadiumAll = new ArrayList<>(arrayStadium);
    }

    @Override
    public int getCount() {
        return arrayStadium.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);
        //Anh Xa
        ImageView stadiumImage = convertView.findViewById(R.id.stadiumImage);
        TextView stadiumName = convertView.findViewById(R.id.stadiumName);
        TextView stadiumAddress = convertView.findViewById(R.id.stadiumAddress);
        TextView stadiumTime = convertView.findViewById(R.id.stadiumTime);

        //stadiumImage.setImageResource(arrayStadium.get(position).getImage());
        stadiumName.setText(arrayStadium.get(position).getStadium_name());
        stadiumAddress.setText(arrayStadium.get(position).getAddress());
        stadiumTime.setText(arrayStadium.get(position).getTime_open());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StadiumModel> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()) {
                filteredList.addAll(arrayStadiumAll);
            } else {
                for (StadiumModel array:arrayStadiumAll){
                    if(array.getStadium_name().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(array);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayStadium.clear();
            arrayStadium.addAll((Collection<? extends StadiumModel>) results.values);
            notifyDataSetChanged();
        }
    };
}