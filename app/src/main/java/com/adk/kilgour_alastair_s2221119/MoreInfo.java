package com.adk.kilgour_alastair_s2221119;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MoreInfo extends AppCompatActivity implements OnMapReadyCallback {
    ResultQuake quake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        // gets info on selected quake
        Intent i = this.getIntent();
        quake = (ResultQuake) i.getSerializableExtra("selectedQuake");

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
            tv.setText(quake.getQuake().getDate());

            // sets time
            tv = findViewById(R.id.valMITime);
            tv.setText(quake.getQuake().getTime());

            // sets depth
            tv = findViewById(R.id.valMIDepth);
            tv.setText(quake.getQuake().getDepth());

            // sets magnitude
            tv = findViewById(R.id.valMIMagnitude);
            tv.setText(quake.getQuake().getMagnitude());

            // sets locality
            tv = findViewById(R.id.valMILocality);
            tv.setText(quake.getQuake().getLocality());

            // sets region
            tv = findViewById(R.id.valMIRegion);
            tv.setText(quake.getQuake().getRegion());

            // sets latitude
            tv = findViewById(R.id.valMILatitude);
            tv.setText(quake.getQuake().getLatitude());

            // sets longitude
            tv = findViewById(R.id.valMILongitude);
            tv.setText(quake.getQuake().getLongitude());
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
                .position(new LatLng(Double.parseDouble(quake.getQuake().getLatitude()), Double.parseDouble(quake.getQuake().getLongitude())))
                .title(quake.getQuake().getDate() + " " + quake.getQuake().getTime()));

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(quake.getQuake().getLatitude()), Double.parseDouble(quake.getQuake().getLongitude())));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(5);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }
}