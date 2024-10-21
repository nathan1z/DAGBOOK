package com.example.dagbook.controlador;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dagbook.R;
import com.example.dagbook.modelo.Contrato;
import com.example.dagbook.modelo.Persona;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class GestionarContra extends AppCompatActivity {
    private Contrato contrato;
    private EditText ci,nombre,telefono,direccion;
    private Boolean isCiImg;
    private ImageView imagenView,viewCi,viewContra;
    private Button btnSave, btnImgCi,btnImgContra,btnAux,btnDelete;
    private Persona titular;
    private Uri uriAux;
    private DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Contratos");
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_contra);
        ci=findViewById(R.id.txtCi);
        nombre=findViewById(R.id.txtNombre);
        telefono=findViewById(R.id.txtTelefono);
        direccion=findViewById(R.id.txtDireccion);
        btnSave = findViewById(R.id.btnAgregarContra);
        btnImgCi = findViewById(R.id.btnTomarFotoCedula);
        btnImgContra = findViewById(R.id.btnTomarFotoContrato);
        btnDelete=findViewById(R.id.btnDelete);
        viewCi=findViewById(R.id.viewCi);
        viewContra=findViewById(R.id.viewContrato);
        ci.setText(getIntent().getStringExtra("ci"));
        nombre.setText(getIntent().getStringExtra("name"));
        telefono.setText(getIntent().getStringExtra("phone"));
        direccion.setText(getIntent().getStringExtra("addres"));
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("ciUri")).into(viewCi);
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("contraUri")).into(viewContra);
        Persona titular=new Persona(nombre.getText().toString(),telefono.getText().toString(),direccion.getText().toString());
        contrato=new Contrato(titular, ci.getText().toString(), getIntent().getStringExtra("ciUri"), getIntent().getStringExtra("contraUri"));
        btnImgCi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCiImg=true;
                btnAux=btnImgCi;
                imagenView=viewCi;
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);

            }
        });
        btnImgContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCiImg=false;
                btnAux=btnImgContra;
                imagenView=viewContra;
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titular.setName(nombre.getText().toString());
                titular.setPhone(telefono.getText().toString());
                titular.setAddress(direccion.getText().toString());
                contrato.setTitular(titular);
                contrato.setCiTitular(ci.getText().toString());
                UploadContratoFirebase();
                Intent intent= new Intent(getApplicationContext(),ContraListActivity.class);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(contrato.getCiTitular()).removeValue();
                Toast.makeText(GestionarContra.this,"Contrato Eliminado",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),ContraListActivity.class);
                startActivity(intent);

            }
        });
    }
    private void generateUrlImg(Uri uri){
        StorageReference file= storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(isCiImg){
                            contrato.setCiImgUri(uri.toString());
                        }else{
                            contrato.setContratoImgUri(uri.toString());
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GestionarContra.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadContratoFirebase() {
        reference.child(contrato.getCiTitular()).setValue(contrato);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver= getContentResolver();
        MimeTypeMap map= MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK && data!= null)
        {
            uriAux = data.getData();
            imagenView.setImageURI(uriAux);
            generateUrlImg(uriAux);
        }
    }
}