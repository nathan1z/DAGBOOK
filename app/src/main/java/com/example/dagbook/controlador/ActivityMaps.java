package com.example.dagbook.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.dagbook.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMaps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    EditText txtLatitud, txtLongitud;
    GoogleMap nMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
    txtLatitud = findViewById(R.id.txtLatitud);
    txtLongitud = findViewById(R.id.txtLongitud);

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        nMap = googleMap;
        this.nMap.setOnMapClickListener(this);
        this.nMap.setOnMapLongClickListener(this);
        LatLng ecuador = new LatLng(-0.3148217,-78.4589914);
        nMap.addMarker(new MarkerOptions().position(ecuador).title("Ecuador"));
        nMap.moveCamera(CameraUpdateFactory.newLatLng(ecuador));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
    txtLatitud.setText(""+latLng.latitude);
    txtLongitud.setText(""+latLng.longitude);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        txtLatitud.setText(""+latLng.latitude);
        txtLongitud.setText(""+latLng.longitude);
    }
}