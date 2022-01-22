package com.example.findurtreatment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HmainpageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fl=new ArrayList<>();
    private List<String> sl=new ArrayList<>();
    public HmainpageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return sl.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fl.get(position);
    }

    @Override
    public int getCount() {
        return fl.size();
    }
    public void addfragment(Fragment fg,String title){
        fl.add(fg);
        sl.add(title);
    }
}
