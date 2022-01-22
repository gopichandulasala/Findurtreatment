package com.example.findurtreatment;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;


public class Hossignuptab extends Fragment {
    TextView name,semail,apass,scopas;
    Button signup;
    int SELECT_PICTURE=200;
    Uri selectedImageUri;
    ImageView im;
    FirebaseFirestore fr;
    private FirebaseAuth mauth;
    private Object User;
    Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.hossignuptab,container,false);
        mauth = FirebaseAuth.getInstance();
        name=root.findViewById(R.id.name);
        im=root.findViewById(R.id.img1);
        fr=FirebaseFirestore.getInstance();
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagechooser();
            }
        });

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    im.setImageURI(selectedImageUri);
                }
            }
        }
    }


    private void imagechooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
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
        Map<String,Object>User=new HashMap<>();
        User.put("Name",name1);
        User.put("email",mail);
        fr.collection("Users").add(User).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(getActivity(),"sucessfully added",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Failure",Toast.LENGTH_LONG).show();
            }
        });

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
        if(selectedImageUri!=null){
            ProgressDialog progressDialog
                    = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
        }

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

