package com.example.gmaps;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gmaps.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button go = (Button) findViewById(R.id.btnGo);
        go.setOnClickListener(op);

        Button cari = (Button) findViewById(R.id.btnCari);
        cari.setOnClickListener(op);
    }

    private void setSupportActionBar(Toolbar myToolbar) {
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

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnGo){
                sembunyikanKeyboard(view);
                gotoLokasi();
            } else if (view.getId() == R.id.btnCari) {
                try {
                    goCari();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    private void goCari() throws IOException {
        EditText location = findViewById(R.id.etLokasi);
        Geocoder g = new Geocoder(getBaseContext());
        try {
            List<Address> addressList = g.getFromLocationName(location.getText().toString(), 1);
            Address address = addressList.get(0);
            String addressLine =  address.getAddressLine(0);
            double latitude = address.getLatitude();
            double longitude = address.getLongitude();

            Toast.makeText(getBaseContext(),"Found " + addressLine,Toast.LENGTH_LONG).show();
            EditText zoom = findViewById(R.id.etZoom);
            float dblzoom = Float.parseFloat(zoom.getText().toString());
            Toast.makeText(this,"Move to " + addressLine + " Lat:" + latitude + " Long:" + longitude,Toast.LENGTH_LONG).show();
            gotoPeta(latitude, longitude, dblzoom);
            EditText lat = findViewById(R.id.etLat);
            EditText lng = findViewById(R.id.etLong);
            lat.setText(Double.toString(latitude));
            lng.setText(Double.toString(longitude));

            double dbllat = Double.parseDouble(lat.getText().toString());
            double dbllng = Double.parseDouble(lng.getText().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gotoLokasi(){
        EditText lat = (EditText) findViewById(R.id.etLat);
        EditText lng = (EditText) findViewById(R.id.etLong);
        EditText zoom = (EditText) findViewById(R.id.etZoom);

        Double dbllat = Double.parseDouble(lat.getText().toString());
        Double dbllng = Double.parseDouble(lng.getText().toString());
        Float fltzoom = Float.parseFloat(zoom.getText().toString());

        Toast.makeText(this, "Move to Lat:"+dbllat+" Long:"+dbllng, Toast.LENGTH_LONG).show();
        gotoPeta(dbllat,dbllng,fltzoom);
    }

    private void sembunyikanKeyboard(View v){
        InputMethodManager a = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    private void gotoPeta(Double lat, Double lng, Float z){
        LatLng LokasiBaru = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(LokasiBaru).title("Marker in "+lat+": "+lng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LokasiBaru,z));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.normal) mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        else if(item.getItemId() == R.id.hibryd) mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        else if(item.getItemId() == R.id.terrain) mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        else if(item.getItemId() == R.id.sattelite) mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        else if(item.getItemId() == R.id.none) mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        return super.onOptionsItemSelected(item);
    }
}