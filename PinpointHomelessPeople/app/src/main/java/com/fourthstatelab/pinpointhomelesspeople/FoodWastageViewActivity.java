package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FoodWastageViewActivity extends AppCompatActivity {
TextView name,quantity,type,address,phone;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodwastagedetailsview);

        name=(TextView)findViewById(R.id.name_foodwastage_provider);
        quantity=(TextView)findViewById(R.id.quantity_foodwastage);
        type=(TextView)findViewById(R.id.type_of_food);
        address=(TextView)findViewById(R.id.address_food_wastage_provider);
        phone=(TextView)findViewById(R.id.phone_foodwastage_provider);

        intent=getIntent();

        int index=intent.getIntExtra("index",0);
        FoodDistribution foodDistribution=Data_holder.Food_distribution.get(index);

        name.setText(foodDistribution.name_of_provider);
        quantity.setText(foodDistribution.quantity+"");
        if(foodDistribution.veg_nonveg==0)
        {
            type.setText("Veg");
        }
        else
            type.setText("Non-Veg");

        address.setText(foodDistribution.address);
        phone.setText(foodDistribution.phone_number);

    }
}
