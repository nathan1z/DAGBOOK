package com.example.dagbook.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dagbook.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

public class LoginActivity extends AppCompatActivity {
EditText name,password;
Button log_btn;
TextView reg_log;
String str_name,str_passw;
    ImageView google_img;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.name_log);
        password=findViewById(R.id.password_log);
        log_btn=findViewById(R.id.login_btn);
        reg_log=findViewById(R.id.register_tv_log);
        //Autenticacion GOOGLE
        google_img = findViewById(R.id.google);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
        google_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

        //Envio a Registro
        reg_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Boton Login
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }
    //FUNCIONES
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            }catch(ApiException e){
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void SignIn() {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    private void HomeActivity() {
        finish();
        Intent intent= new Intent(getApplicationContext(), InicioActivity.class);
        startActivity(intent);
    }

    private void Validation() {
        str_name = name.getText().toString();
        str_passw = password.getText().toString();

        if (str_name.isEmpty()) {
            name.setError("Por favor llenar este campo");
            name.requestFocus();
        }

        if (str_passw.isEmpty()) {
            password.setError("Por favor llenar este campo");
            name.requestFocus();
            return;
        }
        checkBD();
    }

    private void checkBD() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(str_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String db_pass= snapshot.child("password").getValue(String.class);
                    if(str_passw.equals(db_pass))
                    {
                        Intent intent= new Intent(getApplicationContext(), InicioActivity.class);
                        intent.putExtra("name",str_name);
                        Toast.makeText(LoginActivity.this,"Ingreso de Sesion Exitoso",Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"No existe la cuenta",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}