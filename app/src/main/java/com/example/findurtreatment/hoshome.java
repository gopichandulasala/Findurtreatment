package com.example.findurtreatment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class hoshome extends AppCompatActivity {
    private FirebaseAuth fauth;
    TabLayout tb;
    ViewPager viewPager;
    Toolbar to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoshome);
        to=findViewById(R.id.tob);
        setSupportActionBar(to);
        tb=findViewById(R.id.tabla);
        viewPager=findViewById(R.id.viewp);
        HmainpageAdapter hmp=new HmainpageAdapter(getSupportFragmentManager());
        hmp.addfragment(HosAppointmentstodayfrag.getInstance(),"Today Appointments");
        hmp.addfragment(Pendingrequestsfragment.getInstance(),"Pending Requests");
        hmp.addfragment(HosAcceptedappointments.getInstance(),"Accepted Requests");
        viewPager.setAdapter(hmp);
        tb.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
     int id=item.getItemId();
     if (id==R.id.edit){
         Intent in=new Intent(getApplicationContext(),Hoseditprofile.class);
         startActivity(in);
         return true;
     }
    return super.onOptionsItemSelected(item);
    }

    public void logout(MenuItem item) {
        Intent in=new Intent(getApplicationContext(),hoslogin.class);
        startActivity(in);
       FirebaseAuth.AuthStateListener aul=new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               if(firebaseAuth.getCurrentUser()!=null);
               {
                   firebaseAuth.signOut();

               }
           }
       };


    }
}