package com.example.dagbook.controlador;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import kotlinx.coroutines.channels.TickerChannelsKt;

public class ContractActivity extends AppCompatActivity {
    private EditText ci,nombre,telefono,direccion;
    private Boolean isCiImg;
    private ImageView imagenView,viewCi,viewContra;
    private Button btnSave, btnImgCi,btnImgContra,btnAux;
    private Contrato contrato;
    private Persona titular;
    private Uri uriAux;
    private DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Contratos");
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        ci=findViewById(R.id.txtCi);
        nombre=findViewById(R.id.txtNombre);
        telefono=findViewById(R.id.txtTelefono);
        direccion=findViewById(R.id.txtDireccion);
        btnSave = findViewById(R.id.btnAgregarContra);
        btnImgCi = findViewById(R.id.btnTomarFotoCedula);
        btnImgContra = findViewById(R.id.btnTomarFotoContrato);
        viewCi=findViewById(R.id.viewCi);
        viewContra=findViewById(R.id.viewContrato);
        titular=new Persona();
        contrato=new Contrato();
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
                if(uriAux !=null){
                    titular.setName(nombre.getText().toString());
                    titular.setPhone(telefono.getText().toString());
                    titular.setAddress(direccion.getText().toString());
                    contrato.setTitular(titular);contrato.setCiTitular(ci.getText().toString());
                    UploadContratoFirebase();
                }
                else{
                    Toast.makeText(ContractActivity.this,"Por favor selecciona una Foto",Toast.LENGTH_SHORT).show();
                }
                Intent intent= new Intent(getApplicationContext(),ContractActivity.class);
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
                Toast.makeText(ContractActivity.this,"Error",Toast.LENGTH_SHORT).show();
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
            btnAux.setVisibility(View.GONE);
            generateUrlImg(uriAux);
        }
    }
}