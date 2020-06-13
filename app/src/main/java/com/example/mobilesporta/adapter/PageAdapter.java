package com.example.mobilesporta.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mobilesporta.fragment.club.CommentTabFragment;
import com.example.mobilesporta.fragment.club.DescriptionTabFragment;
import com.example.mobilesporta.fragment.club.HistoryTabFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    private String clubId;
    private String userId;

    public PageAdapter(@NonNull FragmentManager fm, int numOfTabs, String clubId, String userId) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.clubId = clubId;
        this.userId = userId;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("club_id", clubId);
        bundle.putString("user_id", userId);
        switch (position) {
            case 0:
                DescriptionTabFragment descriptionTabFragment = new DescriptionTabFragment();
                descriptionTabFragment.setArguments(bundle);
                return descriptionTabFragment;
            case 1:
                HistoryTabFragment historyTabFragment = new HistoryTabFragment();
                historyTabFragment.setArguments(bundle);
                return historyTabFragment;
            case 2:
                CommentTabFragment commentTabFragment = new CommentTabFragment();
                commentTabFragment.setArguments(bundle);
                return commentTabFragment;
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
