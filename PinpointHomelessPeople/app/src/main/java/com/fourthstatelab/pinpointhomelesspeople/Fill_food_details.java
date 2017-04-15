package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Fill_food_details extends AppCompatActivity {
    EditText name,address,quantity,phonenumber;
    Button okay;
    DatabaseReference database;
    FirebaseAuth firebaseAuth;
    Intent prev_intent;
    CheckBox cb_veg,cb_nveg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_food_details);

        database= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();

        prev_intent=getIntent();
        String z=prev_intent.getStringExtra("lat_lon_jason");

        final Location_Data locdata=new Gson().fromJson(z,new TypeToken<Location_Data>(){}.getType());

        name=(EditText)findViewById(R.id.nameoforg);
        address=(EditText)findViewById(R.id.address);
        quantity=(EditText)findViewById(R.id.quantity);
        phonenumber=(EditText)findViewById(R.id.phone);
        okay=(Button)findViewById(R.id.done_food);

        cb_nveg=(CheckBox)findViewById(R.id.cb_nveg);
        cb_veg=(CheckBox)findViewById(R.id.cb_veg);

        cb_nveg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) cb_veg.setChecked(false);
                else if(!cb_nveg.isChecked()) cb_nveg.setChecked(true);
            }
        });

        cb_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) cb_nveg.setChecked(false);
                else if(!cb_veg.isChecked()) cb_veg.setChecked(true);
            }
        });

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodDistribution food_details=new FoodDistribution();
                food_details.address=address.getText().toString();
                food_details.name_of_provider=name.getText().toString();
                food_details.phone_number=phonenumber.getText().toString();
                food_details.quantity=Integer.parseInt(quantity.getText().toString());

                food_details.veg_nonveg = getFoodType();

                food_details.loc_data=locdata;

                upload_details_to_firebase(food_details);
            }
        });

    }

    public void upload_details_to_firebase(FoodDistribution food)
    {
        try {
            database.child("FoodDonate").child(System.currentTimeMillis() + "").setValue(food);
        }
        catch(Exception e) {

        }
        Intent myintent=new Intent(Fill_food_details.this,NavigationCentre.class);
        startActivity(myintent);
        finish();
    }

    int getFoodType(){
        return cb_veg.isChecked() ? 0 : 1;
    }
}
