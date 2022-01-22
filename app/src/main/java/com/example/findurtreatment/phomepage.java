package com.example.findurtreatment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class phomepage extends AppCompatActivity {
    RecyclerView rcv;
    DatabaseReference dr;
    Precycleradap adp;
    ArrayList<User> list;
    Context cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phomepage);
        ActionBar b=getSupportActionBar();
        b.setTitle("Welcome");
        b.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        rcv=findViewById(R.id.recl);
        dr= FirebaseDatabase.getInstance().getReference("Hospitals");
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adp=new Precycleradap(list,this);
        rcv.setAdapter(adp);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot st:snapshot.getChildren()){
                    User us=st.getValue(User.class);
                    if(us!=null) {
                        list.add(us);
                    }
                }
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                processsearch(s);
                return false;
            }


        });
        return true;
    }
    private void processsearch(String s) {

        Query query=dr.orderByChild("Hospitals").startAt(s).endAt(s+"\uf8ff");

    }




}