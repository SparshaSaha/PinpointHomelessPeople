package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.fourthstatelab.pinpointhomelesspeople.Utility.nunito_Extrabold;
import static com.fourthstatelab.pinpointhomelesspeople.Utility.nunito_bold;

public class FoodWastageViewActivity extends FragmentActivity implements OnMapReadyCallback {
TextView name,quantity,type,address,phone;
    Intent intent;
    int index;
    FoodDistribution foodDistribution;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodwastagedetailsview);

        name=(TextView)findViewById(R.id.nameofhomeless);
        quantity=(TextView)findViewById(R.id.quantity);
        type=(TextView)findViewById(R.id.mytype);
        address=(TextView)findViewById(R.id.address);
        phone=(TextView)findViewById(R.id.ph_no);

        name.setTypeface(nunito_Extrabold);
        quantity.setTypeface(nunito_bold);
        type.setTypeface(nunito_bold);
        address.setTypeface(nunito_bold);
        phone.setTypeface(nunito_bold);

        intent=getIntent();

        index=intent.getIntExtra("index",0);
        foodDistribution=Data_holder.Food_distribution.get(index);

        name.setText(foodDistribution.name_of_provider);
        quantity.setText(foodDistribution.quantity+" Serves");
        if(foodDistribution.veg_nonveg==0)
        {
            type.setText("Veg");
        }
        else
            type.setText("Non-Veg");

        address.setText(foodDistribution.address);
        phone.setText("Ph: "+foodDistribution.phone_number);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int permissionCheck = ContextCompat.checkSelfPermission(FoodWastageViewActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (ContextCompat.checkSelfPermission(FoodWastageViewActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(FoodWastageViewActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1123);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        LatLng latlong=new LatLng(foodDistribution.loc_data.latitude,foodDistribution.loc_data.longitude);
        Marker mymarker=mMap.addMarker(new MarkerOptions().position(latlong).title(foodDistribution.name_of_provider));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15));
        mymarker.showInfoWindow();
        mymarker.setTag(index);
    }
}
