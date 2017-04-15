package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.fourthstatelab.pinpointhomelesspeople.Utility.nunito_bold;
import static com.fourthstatelab.pinpointhomelesspeople.Utility.nunito_reg;

public class HomelessViewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int index;
    Homeless curr_homeless;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertdialoghomelessdetails);
        Utility.setStatusBar(getWindow(),getApplicationContext());
        TextView name=(TextView)findViewById(R.id.nameofhomeless);
        TextView age=(TextView)findViewById(R.id.ageofhomeless);
        TextView sex=(TextView)findViewById(R.id.sexofhomeless);
        TextView other=(TextView)findViewById(R.id.otherdetailshomeless);
        Intent prev_intent=getIntent();
        index=prev_intent.getIntExtra("index",0);

        curr_homeless=Data_holder.Homeless_list.get(index);
        name.setText(curr_homeless.name+"");
        name.setTypeface(nunito_bold);
        age.setTypeface(nunito_reg);
        sex.setTypeface(nunito_reg);
        other.setTypeface(nunito_reg);
        age.setText("Age: "+curr_homeless.age);
        sex.setText(curr_homeless.gender);
        other.setText(curr_homeless.other);
        StorageReference storage= FirebaseStorage.getInstance().getReference();
        StorageReference s1=storage.child("images/"+name.getText().toString()+".jpg");
        ImageView imageView=(ImageView)findViewById(R.id.pictureofhomeless);
        if(curr_homeless.pic_data_url==null || curr_homeless.pic_data_url.equals("nil")==false) {

            Glide.with(this).using(new FirebaseImageLoader()).load(s1).into(imageView);
        }
        else
        {

        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int permissionCheck = ContextCompat.checkSelfPermission(HomelessViewActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (ContextCompat.checkSelfPermission(HomelessViewActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(HomelessViewActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1123);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        LatLng latlong=new LatLng(curr_homeless.loc_data.latitude,curr_homeless.loc_data.longitude);
        Marker mymarker=mMap.addMarker(new MarkerOptions().position(latlong).title(curr_homeless.name));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15));
        mymarker.showInfoWindow();
        mymarker.setTag(index);
    }

}
