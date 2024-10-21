package com.example.dagbook.controlador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.dagbook.ProspectList2;
import com.example.dagbook.RecyclerView_Config;
import com.example.dagbook.R;
import com.example.dagbook.RecyclerView_Config2;
import com.example.dagbook.modelo.Persona;
import com.example.dagbook.modelo.Persona2;

import java.util.ArrayList;
import java.util.List;

public class ProspectList extends AppCompatActivity {
    private RecyclerView reclyclerView;
    SearchView buscarConfi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospectlist);
        buscarConfi = findViewById(R.id.nameFind);

       reclyclerView=findViewById(R.id.userlist);
       new FireBaseDataBaseHelper().readProspects(new FireBaseDataBaseHelper.DataStatus() {
           @Override
           public void DataIsLoaded(List<Persona2> persona, List<String> keys) {
               findViewById(R.id.progressBar3).setVisibility(View.GONE);
               new RecyclerView_Config2().setConfig( reclyclerView, ProspectList.this,persona,keys);
               buscarConfi.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                       new RecyclerView_Config().setConfig(reclyclerView, ProspectList.this, milista, keys);
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