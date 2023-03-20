/*
Name: Alastair Kilgour
SN: S2221119
Programme: Computer
*/

package com.adk.kilgour_alastair_s2221119;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MoreInfo extends AppCompatActivity implements OnMapReadyCallback {
    Earthquake quake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        getSupportActionBar().hide();

        // gets info on selected quake
        Intent i = getIntent();
        quake = (Earthquake) i.getSerializableExtra("selectedQuake");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        loadData();
        loadMap();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadData() {

        if (quake != null) { // makes sure a quake has actually be passed
            // sets date
            TextView tv = findViewById(R.id.valMIDate);
            tv.setText(quake.getDate());

            // sets time
            tv = findViewById(R.id.valMITime);
            tv.setText(quake.getTime());

            // sets depth
            tv = findViewById(R.id.valMIDepth);
            tv.setText(quake.getDepth());

            // sets magnitude
            tv = findViewById(R.id.valMIMagnitude);
            tv.setText(quake.getMagnitude());

            // sets locality
            tv = findViewById(R.id.valMILocality);
            tv.setText(quake.getLocality());

            // sets region
            tv = findViewById(R.id.valMIRegion);
            tv.setText(quake.getRegion());

            // sets latitude
            tv = findViewById(R.id.valMILatitude);
            tv.setText(quake.getLatitude());

            // sets longitude
            tv = findViewById(R.id.valMILongitude);
            tv.setText(quake.getLongitude());
        }
    }

    private void loadMap() {
        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Get a handle to the GoogleMap object and display marker.
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(quake.getLatitude()), Double.parseDouble(quake.getLongitude())))
                .title(quake.getDate() + " " + quake.getTime()));

        CameraUpdate center=
                CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(quake.getLatitude()), Double.parseDouble(quake.getLongitude())), 5);

        googleMap.moveCamera(center);
    }
}