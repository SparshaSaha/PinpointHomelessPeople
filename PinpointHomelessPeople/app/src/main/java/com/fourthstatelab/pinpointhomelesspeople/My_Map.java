package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;

import com.firebase.client.core.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.zip.Inflater;

public class My_Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        //LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
        mMap.setMyLocationEnabled(true);
        LocationManager locmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location loc=locmanager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        LatLng latlng=new LatLng(loc.getLatitude(),loc.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));

        //Add Marker on clicked location
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Location of Homeless"));
                AlerDialog_to_proceed(latLng);
            }
        });

        //Remove marker on click
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.remove();
                return false;
            }
        });


    }

    public void AlerDialog_to_proceed(final LatLng latLng)
    {
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater= this.getLayoutInflater();
        android.view.View alertview=layoutInflater.inflate(R.layout.alert_dialog_layout,null);
        alertdialogbuilder.setView(alertview);
        Button yes,no;
        yes=(Button)alertview.findViewById(R.id.yes);
        no=(Button)alertview.findViewById(R.id.no);
        final AlertDialog alert=alertdialogbuilder.create();
        alert.show();

        yes.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                alert.cancel();
                Location_Data locdata=new Location_Data();
                locdata.latitude=latLng.latitude;
                locdata.longitude=latLng.longitude;
                Gson jason=new Gson();
                String loc=jason.toJson(locdata);
                Intent intent=new Intent(My_Map.this,Fill_homeless_details.class);
                intent.putExtra("lat_lon_jason",loc);
                startActivity(intent);
                finish();

            }
        });

        no.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                alert.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(My_Map.this,NavigationCentre.class));
        finish();
        super.onBackPressed();
    }
}
