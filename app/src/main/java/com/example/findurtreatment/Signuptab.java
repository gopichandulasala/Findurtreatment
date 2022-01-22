package com.example.findurtreatment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class Signuptab extends Fragment {
    TextView name,semail,apass,scopas;
    Button signup;
    ProgressBar pb;
     private FirebaseAuth mauth;
     FirebaseFirestore fb;
     StorageReference sd;
    private Object User;
    Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signinfragment,container,false);
        mauth = FirebaseAuth.getInstance();
        name=root.findViewById(R.id.name);
        semail=root.findViewById(R.id.mailids);
        apass=root.findViewById(R.id.passwords);
        scopas=root.findViewById(R.id.passwordscon);
        signup=root.findViewById(R.id.signin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mauth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    private void registeruser() {
        String mail = semail.getText().toString();
        String name1 = name.getText().toString();
        String pa = apass.getText().toString();
        String cop = scopas.getText().toString();
        if (name1.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();

        }
        if (pa.isEmpty()) {
            apass.setError("password is required");
            apass.requestFocus();

        }
        if (pa.length() < 6) {
            apass.setError("atleast 6 characters");
            apass.requestFocus();
        }
        if (mail.isEmpty()) {
            semail.setError("mail is required");
            semail.requestFocus();


        }
        if (cop.isEmpty()) {
            scopas.setError("password is required");
            scopas.requestFocus();

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            semail.setError("please provide vaild adress");
            semail.requestFocus();
        }
        mauth.createUserWithEmailAndPassword(mail,pa)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getActivity(),"account created",Toast.LENGTH_LONG);
                            FirebaseUser user = mauth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        if (user!=null){
            Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();
            
        }
        else{
            Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
        }
    }


}

