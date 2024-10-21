package com.example.dagbook.controlador;

import androidx.annotation.NonNull;

import com.example.dagbook.modelo.Persona;
import com.example.dagbook.modelo.Persona2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseDataBaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Persona2> propects= new ArrayList<>();
    public interface  DataStatus{
        void DataIsLoaded (List<Persona2> persona, List<String> keys);
        void DataIsInsert();
        void DataIsUpdated();
        void DataIsDelete();
    }
    public FireBaseDataBaseHelper() {
        mDatabase= FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Prospects");    }
    public void readProspects(final DataStatus dataStatus ){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            propects.clear();
            List<String> keys= new ArrayList<>();
            for (DataSnapshot keyNode: snapshot.getChildren())
            {
                keys.add(keyNode.getKey());
                Persona2 persona= keyNode.getValue(Persona2.class);
                propects.add(persona);
            }
            dataStatus.DataIsLoaded(propects,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updateProspect(String key,Persona2 persona,final DataStatus dataStatus){
    mReference.child(key).setValue(persona)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    dataStatus.DataIsUpdated();
                }
            });

    }
    public void deleteProspect(String key,final DataStatus dataStatus){
        mReference.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsDelete();
                    }
                });
    }

}
