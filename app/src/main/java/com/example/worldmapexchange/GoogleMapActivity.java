package com.example.worldmapexchange;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LatLng mChosenLocation = null;
    private Address mChosenLocationAddress = null;
    private Geocoder mGeocoder;
    private TextView mTvCountryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGeocoder=new Geocoder(this, Locale.getDefault());
        mTvCountryName=findViewById(R.id.tvCountryName);
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"My location disabled", Toast.LENGTH_SHORT);
        }
        else mMap.setMyLocationEnabled(true);

        mMap.setOnMapClickListener(this);
        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         */
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mChosenLocation=latLng;
        //Get the country at mChosenLocation (prefer AsyncTask)
        //Update marker and selected country name
        new AsyncTaskUpdateChosenLocation().execute(mChosenLocation);

    }

    public void btnSubmitOnClick(View view) {
        if (mChosenLocation == null) {
            //Choose no country
            Toast.makeText(this,"You choose no country.", Toast.LENGTH_SHORT).show();
        }
        else if (mChosenLocationAddress == null) {
            //Choose somewhere, but cannot get the country
            Toast.makeText(this,"You choose no country land.", Toast.LENGTH_SHORT).show();
        }
        else {
            //todo call API to get destination currencies?
            finish();
        }
    }

    class AsyncTaskUpdateChosenLocation extends AsyncTask<LatLng, Void, Address> {

        @Override
        protected Address doInBackground(LatLng... latLngs) {
            for (LatLng latlng : latLngs) {
                try {
                    List<Address> addressList=mGeocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
                    if (addressList.isEmpty()) return null;
                    else return addressList.get(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    //next line produce an error
                    //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Address address) {
            //never prune out information.
            mChosenLocationAddress = address;
            if (address != null)
                mTvCountryName.setText(address.getCountryName());
            else mTvCountryName.setText("Not a country. Please select a country");
        }
    }

    @Override
    protected void onDestroy() {
        //todo save something to class Resource
        super.onDestroy();
    }
}