package com.example.dagbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.dagbook.controlador.FireBaseDataBaseHelper;
import com.example.dagbook.modelo.Persona2;

import java.util.ArrayList;
import java.util.List;

public class ProspectList2 extends AppCompatActivity {
    private RecyclerView reclyclerView;
    SearchView buscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospect_list2);
        reclyclerView=findViewById(R.id.userlist);
        buscar = findViewById(R.id.findName);
        new FireBaseDataBaseHelper().readProspects(new FireBaseDataBaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Persona2> persona, List<String> keys) {
                findViewById(R.id.progressBar3).setVisibility(View.GONE);
                new RecyclerView_Config2().setConfig( reclyclerView, ProspectList2.this,persona,keys);
                buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        ArrayList<Persona2> milista = new ArrayList<>();
                        for (Persona2 obj : persona) {
                            if (obj.getName().toLowerCase().contains(newText.toLowerCase())) {
                                milista.add(obj);
                            }
                        }
                        new RecyclerView_Config().setConfig(reclyclerView, ProspectList2.this, milista, keys);
                        return true;
                    }
                });
            }

            @Override
            public void DataIsInsert() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDelete() {

            }
        });
    }
}