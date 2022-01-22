package com.example.findurtreatment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findurtreatment.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
EditText name,age,problem,phonemunber;
Button b1;
Intent in;
ActivityRegisterBinding binding;
DatabaseReference ref;

    public register() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        in=getIntent();
        setContentView(R.layout.activity_register);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        name=findViewById(R.id.pname);
        age=findViewById(R.id.age);
        problem=findViewById(R.id.prob);
        phonemunber=findViewById(R.id.phone);
        b1=findViewById(R.id.book);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

                    sendmessage();
                }
                else{
                    ActivityCompat.requestPermissions(register.this,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
    }
    private void sendmessage() {
        String na,ag,pr,po,nam;
        nam=in.getStringExtra("name");
        ref= FirebaseDatabase.getInstance().getReference("Hospitals");
        na=name.getText().toString();
        ag=age.getText().toString();
        pr=problem.getText().toString();
        po=phonemunber.getText().toString();
        String message="NAME:"+na+"\n"+"age"+ag+"\n"+"Problem:"+pr+"\n"+"Contact details:"+po;
        String call=in.getStringExtra("number");
        SmsManager sma=SmsManager.getDefault();
        sma.sendTextMessage(call,null,message,null,null);
        Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            sendmessage();
        }
        else{
            Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}