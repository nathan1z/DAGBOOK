package com.example.dagbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dagbook.controlador.FireBaseDataBaseHelper;
import com.example.dagbook.modelo.Persona;
import com.example.dagbook.modelo.Persona2;

import java.util.List;

public class ProspectDetailsActivity extends AppCompatActivity {
private EditText nametxt,phonetxt,addresstxt,mailtxt;
private Button btn_update,btn_delete;
private String key,name,phone,address,mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        key=getIntent().getStringExtra("key");
        name=getIntent().getStringExtra("name");
        phone=getIntent().getStringExtra("phone");
        address=getIntent().getStringExtra("address");
        mail= getIntent().getStringExtra("mail");

        setContentView(R.layout.activity_prospect_details);
        nametxt= findViewById(R.id.name);
        nametxt.setText(name);
        phonetxt= findViewById(R.id.phone);
        phonetxt.setText(phone);
        addresstxt= findViewById(R.id.address);
        addresstxt.setText(address);
        mailtxt= findViewById(R.id.correoE);
        mailtxt.setText(mail);

        btn_update= findViewById(R.id.btn_update);
        btn_delete= findViewById(R.id.btn_delete);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona2 persona= new Persona2();
                persona.setName(nametxt.getText().toString());
                persona.setPhone(phonetxt.getText().toString());
                persona.setAddress(addresstxt.getText().toString());
                persona.setMail(mailtxt.getText().toString());
                new FireBaseDataBaseHelper().updateProspect(key, persona, new FireBaseDataBaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Persona2> persona, List<String> keys) {

                    }

                    @Override
                    public void DataIsInsert() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(ProspectDetailsActivity.this,"Los datos fueron exitosamente Actualizados",Toast.LENGTH_SHORT ).show();
                    }

                    @Override
                    public void DataIsDelete() {

                    }
                });
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FireBaseDataBaseHelper().deleteProspect(key, new FireBaseDataBaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Persona2> persona, List<String> keys) {

                    }

                    @Override
                    public void DataIsInsert() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDelete() {
                        Toast.makeText(ProspectDetailsActivity.this,"Prospecto Eliminado",Toast.LENGTH_SHORT ).show();
                        finish(); return;
                    }
                });
            }
        });
    }
}