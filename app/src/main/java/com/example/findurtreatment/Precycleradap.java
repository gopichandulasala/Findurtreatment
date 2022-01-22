package com.example.findurtreatment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Precycleradap extends RecyclerView.Adapter<Precycleradap.Myviewholder>{
Context context;
    ArrayList<User>list;
    StorageReference drf;

    public Precycleradap(ArrayList<User> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override

    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
     User us=list.get(position);
     holder.t1.setText(us.getName());
     holder.t2.setText(us.getDetails());
     holder.t3.setText(us.getNumber());
     holder.t4.setText(us.getAdd());
     holder.b1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent in=new Intent(view.getContext(),register.class);
             in.putExtra("number",us.getNumber());
             view.getContext().startActivity(in);
         }
     });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{
TextView t1,t2,t3,t4;
ImageView im;
Button b1;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.nameho);
            t2=itemView.findViewById(R.id.hodet);
            t4=itemView.findViewById(R.id.details);
            t3=itemView.findViewById(R.id.Contactdetails);
            im=itemView.findViewById(R.id.ho);
            b1=itemView.findViewById(R.id.det);
        }
    }
}

