package com.example.findurtreatment;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Hoseditprofile extends AppCompatActivity {
    private FirebaseAuth fauth;
    Button BSelectImage,submit;
    ImageView IVPreviewImage;
    Uri path;
    String uid;
    String link;
    int SELECT_PICTURE = 200;
    EditText nam,det,phone,add;
    private FirebaseFirestore fs;
    private DatabaseReference derf;
    private StorageReference strf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoseditprofile);
        fauth=FirebaseAuth.getInstance();
        uid=fauth.getCurrentUser().getUid();
        derf= FirebaseDatabase.getInstance().getReference("Hospitals");
        BSelectImage = findViewById(R.id.choose);
        IVPreviewImage = findViewById(R.id.imageView4);
        nam=findViewById(R.id.hosname);
        add=findViewById(R.id.hosad);
        det=findViewById(R.id.hosdet);
        phone=findViewById(R.id.phone);
        submit=findViewById(R.id.submit);
        fs=FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        strf=storage.getReference();
        BSelectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageChooser();
                }
            });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(path);
                String hname=nam.getText().toString();
                String addd=add.getText().toString();
                String details=det.getText().toString();
                String num=phone.getText().toString();
                nam.setText(hname);
                add.setText(addd);
                det.setText(details);
                phone.setText(num);
                User us=new User(hname,details,addd,num);
                if(uid!=null){
                    derf.child(hname).setValue(us).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(getApplicationContext(),phomepage.class);
                            startActivity(in);
                            finish();


                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to upadte", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });
        }

    private void upload(Uri path) {
        if (path != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference image= strf.child("images/"+ UUID.randomUUID().toString());

            image.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                { progressDialog.dismiss();
                                link=strf.getDownloadUrl().toString();
                                    Toast.makeText(getApplicationContext(),"Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " +(int)progress+ "%");
                                }
                            });

    }
    }

    void imageChooser() {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        }
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                    path = data.getData();
                    Picasso.with(getApplicationContext()).load(path).into(IVPreviewImage);

                }
            }

    }



}
