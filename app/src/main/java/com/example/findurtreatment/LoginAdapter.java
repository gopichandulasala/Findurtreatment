package com.example.findurtreatment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context cont;
    int totaltabs;
    public LoginAdapter(FragmentManager fm,Context context,int totaltabs) {
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
                Logintab lg=new Logintab();
                return lg;
            case 1:
                Signuptab sb=new Signuptab();
                return sb;
            default:
                return null;
        }
    }
}
