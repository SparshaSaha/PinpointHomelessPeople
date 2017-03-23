package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fill_food_details extends AppCompatActivity {
    EditText name,address,quantity,type,phonenumber;
    Button okay;
    DatabaseReference database;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_food_details);

        database= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();

        name=(EditText)findViewById(R.id.nameoforg);
        address=(EditText)findViewById(R.id.address);
        quantity=(EditText)findViewById(R.id.quantity);
        type=(EditText)findViewById(R.id.quantity);
        phonenumber=(EditText)findViewById(R.id.phone);
        okay=(Button)findViewById(R.id.done_food);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodDistribution food_details=new FoodDistribution();
                food_details.address=address.getText().toString();
                food_details.name_of_provider=name.getText().toString();
                food_details.phone_number=name.getText().toString();
                food_details.quantity=Double.parseDouble(quantity.getText().toString());
                if(type.getText().toString().equalsIgnoreCase("veg"))
                    food_details.veg_nonveg=0;
                else
                    food_details.veg_nonveg=1;

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
}
