package com.example.findurtreatment;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Hosloginadapter extends FragmentPagerAdapter{
    private Context cont;
    int totaltabs;
    public Hosloginadapter(FragmentManager fm, Context context, int totaltabs) {
        super(fm);
        this.cont=context;
        this.totaltabs=totaltabs;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }

    public Fragment getItem(int pos){
        switch (pos){
            case 0:
                Hoslogintab lg=new Hoslogintab();
                return lg;
            case 1:
                Hossignuptab sb=new Hossignuptab();
                return sb;
            default:
                return null;
        }
    }
}
