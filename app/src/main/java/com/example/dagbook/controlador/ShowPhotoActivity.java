package com.example.dagbook.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dagbook.vista.AdapterPhoto;
import com.example.dagbook.R;
import com.example.dagbook.modelo.imageModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowPhotoActivity extends AppCompatActivity {
RecyclerView recyclerView;
AdapterPhoto adapter;
ArrayList<imageModel> models;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Imagen");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        models= new ArrayList<>();
        adapter=new AdapterPhoto(models,this);
        recyclerView.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    imageModel model=dataSnapshot.getValue(imageModel.class);
                    models.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}