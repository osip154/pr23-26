package com.example.pr23;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class TrackingActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        Button btnFinish = findViewById(R.id.btn_finish);

        btnFinish.setOnClickListener(v -> {
            startActivity(new Intent(this,
                    DeliverySuccessActivity.class));
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager()
                                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        LatLng start = new LatLng(55.751244, 37.618423);
        LatLng end = new LatLng(55.761244, 37.628423);

        mMap.addMarker(new MarkerOptions()
                .position(start)
                .title("Start"));

        mMap.addMarker(new MarkerOptions()
                .position(end)
                .title("Destination"));

        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(start, 13));

        PolylineOptions route = new PolylineOptions()
                .add(start)
                .add(end)
                .width(10)
                .color(Color.BLUE);

        mMap.addPolyline(route);
    }
}