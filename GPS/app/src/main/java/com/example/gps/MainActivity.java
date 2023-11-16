package com.example.gps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.location.LocationListener;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private LocationManager mylocationManager;
    private LocationListener mylocationListener;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );

        locationPermissionRequest.launch(new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        });

        mylocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mylocationListener = new lokasiListener();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button start = findViewById(R.id.btnStart);
        start.setOnClickListener(op);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button stop = findViewById(R.id.btnStop);
        stop.setOnClickListener(op);
    }

    private class lokasiListener implements LocationListener{
        private TextView txtLat, txtLong, txtTime;
        @Override
        public void onLocationChanged(@NonNull Location location) {
            mMap.clear();

            txtLat = (TextView) findViewById(R.id.tvLat);
            txtLong = (TextView) findViewById(R.id.tvLong);

            txtLat.setText("Lat: "+String.valueOf(location.getLatitude()));
            txtLong.setText("Long: "+String.valueOf(location.getLongitude()));

            Date currentTime = Calendar.getInstance().getTime();

            txtTime = (TextView) findViewById(R.id.tvUpdate);
            txtTime.setText("Last Update: "+String.valueOf(currentTime));

            Double latitude = (Double) location.getLatitude();
            Double longitude = (Double) location.getLongitude();
            Float zoom = 20F;
            gotoPeta(latitude, longitude, zoom);
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnStart){
                updateGPS();
            }
            else if(view.getId() == R.id.btnStop){
                stopGPS();
            }
        }
    };

    private void stopGPS(){
        mylocationManager.removeUpdates(mylocationListener);
    }

    private void updateGPS(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mylocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mylocationListener);

    }

    private void gotoPeta(Double lat, Double lng, Float z){
        LatLng LokasiBaru = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(LokasiBaru).title("Marker in "+lat+": "+lng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LokasiBaru,z));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ITS = new LatLng(-7.2819705, 112.795323);
        mMap.addMarker(new MarkerOptions().position(ITS).title("Marker in ITS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITS,15));
    }

}