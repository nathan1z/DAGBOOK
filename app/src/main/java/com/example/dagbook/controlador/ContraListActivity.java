package com.example.dagbook.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.Control;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dagbook.R;
import com.example.dagbook.modelo.Contrato;
import com.example.dagbook.modelo.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ContraListActivity extends AppCompatActivity {
    private ListView listContraView;
    private ArrayList<Contrato> listContra;
    private ArrayList<HashMap<String,String>> listContraCmp =new ArrayList<HashMap<String,String>>();
    private ArrayAdapter<HashMap<String,String>> arrayAdapterContrato;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contra_list);
        listContraView=findViewById(R.id.listContra);
        inicializarFirebase();
        listContra=new ArrayList<Contrato>();
        listarDatos();
    }

    private void listarDatos(){
        databaseReference.child("Contratos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Contrato c=objSnaptshot.getValue(Contrato.class);
                    listContra.add(c);
                    Persona titular=c.getTitular();
                    HashMap<String,String> contraCmpt=new HashMap<>();
                    contraCmpt.put("Cédula",c.getCiTitular());
                    contraCmpt.put("Nombre",titular.getName());
                    listContraCmp.add(contraCmpt);
                    arrayAdapterContrato=new ArrayAdapter<HashMap<String,String>>(ContraListActivity.this, android.R.layout.simple_list_item_1, listContraCmp);
                    listContraView.setAdapter(arrayAdapterContrato);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listContraView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Contrato c= listContra.get(position);
                Persona titular=c.getTitular();
                Intent intent=new Intent(ContraListActivity.this,GestionarContra.class);
                intent.putExtra("ci",c.getCiTitular());
                intent.putExtra("name",titular.getName());
                intent.putExtra("phone",titular.getPhone());
                intent.putExtra("addres",titular.getAddress());
                intent.putExtra("ciUri",c.getCiImgUri());
                intent.putExtra("contraUri",c.getContratoImgUri());
                startActivity(intent);
            }
        });


    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }

    private void adapterContraList(Contrato c){
    }
}
