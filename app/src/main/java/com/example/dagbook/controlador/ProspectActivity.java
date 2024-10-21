package com.example.dagbook.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dagbook.ProspectList2;
import com.example.dagbook.modelo.Persona;
import com.example.dagbook.R;
import com.example.dagbook.modelo.Persona2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProspectActivity extends AppCompatActivity /*implements View.OnClickListener, AdapterView.OnItemClickListener*/ {

    private Button btnAgregar,btnEnviar;
    private Persona2 prospecto;
    private EditText name,phone,address,mail;
    private String str_name,str_phone,str_address,str_mail;
    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospect);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEnviar= findViewById(R.id.btnEnviar);


        name=findViewById(R.id.txtNombre);
        phone=findViewById(R.id.txtTelefono);
        address=findViewById(R.id.txtDireccion);
        mail= findViewById(R.id.txtEmail);

        progressDialog=new ProgressDialog( this);
        progressDialog.setTitle("Por favor esperar....");
        progressDialog.setCanceledOnTouchOutside(false);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), ProspectList2.class);
                startActivity(intent);
            }
        });

    }

    private void Validation() {
        String msg="Por favor llenar todos los campos campo";
        str_name=name.getText().toString();
        str_address=address.getText().toString() ;
        str_phone=phone.getText().toString();
        str_mail= mail.getText().toString();

        if(str_name.isEmpty()) {
            name.setError(msg);
            name.requestFocus();
        }
        if(str_phone.isEmpty()) {
            phone.setError(msg);
            phone.requestFocus();
        }
        if(!numberCheck(str_phone)) {
            phone.setError(msg);
            phone.requestFocus();
            return;
        }
        if(str_address.isEmpty()) {
            address.setError(msg);
            address.requestFocus();
        }
        if(str_mail.isEmpty()) {
            mail.setError(msg);
            mail.requestFocus();
        }
        createAccount();
    }

    private void createAccount() {
        progressDialog.setMessage("Creando Prospecto....");
        progressDialog.show();
        sendDataToDd();
    }

    private void sendDataToDd() {
        prospecto= new Persona2(str_name,str_phone,str_address,str_mail);
        reference= FirebaseDatabase.getInstance().getReference("Prospects");
        String key= reference.push().getKey();
        reference.child(key).setValue(prospecto).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Intent intent= new Intent(getApplicationContext(),ProspectActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"NOT Registered",Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean numberCheck(String str_phone) {
        Pattern p= Pattern.compile("[0-9]{10}");
        Matcher m= p.matcher(str_phone);
        return m.matches();
    }

}