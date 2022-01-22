package com.example.findurtreatment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class category extends AppCompatActivity {

    Button hos,pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        hos=findViewById(R.id.hos);
        pa=findViewById(R.id.pat);
        hos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),hoslogin.class);
                startActivity(in);
                finish();
            }
        });
        pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),loginpage.class);
                startActivity(in);
                finish();
            }
        });
    }
}