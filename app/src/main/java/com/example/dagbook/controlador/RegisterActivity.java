package com.example.dagbook.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dagbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
  EditText name,password,mail,phone,con_pass;
    TextView signin;
    Button reg_btn;
    String str_name,str_pass,str_mail,str_conf_pass,str_phone;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        mail=findViewById(R.id.mail);
        phone=findViewById(R.id.phone) ;
      con_pass=findViewById(R.id.con_pass);
        signin=findViewById(R.id.sign_tv);
        reg_btn=findViewById(R.id.reg_btn);


        //Base de datos Proceso
        progressDialog=new ProgressDialog( this);
        progressDialog.setTitle("Por favor esperar....");
        progressDialog.setCanceledOnTouchOutside(false);


        //Regreso a LOGIN
signin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
});
    reg_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Validation();
        }
    });
    }

    private void Validation() {
        str_name=name.getText().toString();
        str_mail=mail.getText().toString() ;
        str_pass=password.getText().toString();
        str_conf_pass=con_pass.getText().toString();
        str_phone=phone.getText().toString();
        if(str_name.isEmpty()) {
            name.setError("Por favor llenar este campo");
            name.requestFocus();
        }
        if(str_phone.isEmpty()) {
            phone.setError("Por favor llenar este campo");
            phone.requestFocus();
        }
        if(!numberCheck(str_phone)) {
            phone.setError("Por favor llenar este campo");
            phone.requestFocus();
            return;
        }
        if(str_mail.isEmpty()) {
            mail.setError("Por favor llenar este campo");
            mail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(str_mail).matches()){
            mail.setError("Ingresar un email valido");
            mail.requestFocus();
            return;
        }
        if(str_pass.isEmpty()) {
            password.setError("Por favor llenar este campo");
            password.requestFocus();
            return;
        }
        if(!passwordValidation(str_pass)) {
            password.setError("Ingrese 8 digitos");
            password.requestFocus();
            return;
        }
        if(str_conf_pass.isEmpty()){
            password.setError("Contraseña no confirmada");
            password.requestFocus();
            return;
        }
        createAccount();

    }

    private void createAccount() {
        progressDialog.setMessage("Creaando Cuenta....");
        progressDialog.show();
        sendDataToDd();
    }
///Enviar info a la Base de datos
    private void sendDataToDd() {
        String regtime=""+System.currentTimeMillis();
        HashMap<String,Object>data=new HashMap<>();
        data.put("name",str_name);
        data.put("mail",str_mail);
        data.put("password",str_pass);
        data.put("phone",str_phone);
        //Firebase
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(str_name).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //actualizar DB
                progressDialog.dismiss();

                Intent intent= new Intent(getApplicationContext(), InicioActivity.class);
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

    private boolean passwordValidation(String str_pass) {
        Pattern p= Pattern.compile(".{6}");
        Matcher m= p.matcher(str_pass);
        return m.matches();
    }

    private boolean numberCheck(String str_phone) {
        Pattern p= Pattern.compile("[0-9]{10}");
        Matcher m= p.matcher(str_phone);
        return m.matches();
    }
}