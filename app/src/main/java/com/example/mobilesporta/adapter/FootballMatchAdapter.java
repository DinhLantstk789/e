package com.example.mobilesporta.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mobilesporta.fragment.game.MyFbMatch_TabFragment;
import com.example.mobilesporta.fragment.game.SugesstionsFbMatch_TabFragment;

public class FootballMatchAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public FootballMatchAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SugesstionsFbMatch_TabFragment();
            case 1:
                return new MyFbMatch_TabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
