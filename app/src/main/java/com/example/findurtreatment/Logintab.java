package com.example.findurtreatment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.UserInfo;

public class Logintab extends Fragment {
    TextView tv;
    float v = 0;
    EditText email, pass;
    private FirebaseAuth mAuth;
    Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.loginfragment, container, false);
        tv = root.findViewById(R.id.forgotpass);
        email = root.findViewById(R.id.mailid);
        pass = root.findViewById(R.id.password);
        login = root.findViewById(R.id.loginb);
        tv.setTranslationX(800);
        email.setTranslationX(800);
        pass.setTranslationX(800);
        login.setTranslationX(800);
        tv.setAlpha(v);
        email.setAlpha(v);
        login.setAlpha(v);
        pass.setAlpha(v);
        tv.animate().translationX(0).alpha(1).setDuration(3000).setStartDelay(300).start();
        email.animate().translationX(0).alpha(1).setDuration(3000).setStartDelay(300).start();
        login.animate().translationX(0).alpha(1).setDuration(3000).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(3000).setStartDelay(300).start();
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String password = pass.getText().toString();
                mAuth.signInWithEmailAndPassword(em, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getActivity(), "Login Sucees", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    emailAddress = user.getEmail();
                    mAuth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                       Toast.makeText(getContext(),"emailsent",Toast.LENGTH_SHORT);
                                    }
                                }
                            });

                }


            }
        });
        return root;
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(getActivity(), "Account doesnt exists", Toast.LENGTH_LONG);
        } else {
            reload();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    private void reload() {
        Intent intent = new Intent(getActivity(), phomepage.class);
        startActivity(intent);
        getActivity().finish();

    }
}

