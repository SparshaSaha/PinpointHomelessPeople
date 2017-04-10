package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Fill_homeless_details extends AppCompatActivity {
Intent prev_intent;
    DatabaseReference database;
    FirebaseAuth firebaseAuth;
    EditText name,gender,age,other;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_homeless_details);
        database=FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();

        name=(EditText)findViewById(R.id.nameofhomeless);
        gender=(EditText)findViewById(R.id.gender);
        age=(EditText)findViewById(R.id.ageofhomeless);
        other=(EditText)findViewById(R.id.other);
        done=(Button)findViewById(R.id.done);

        prev_intent=getIntent();
        final String z=prev_intent.getStringExtra("lat_lon_jason");
        Toast.makeText(this, z, Toast.LENGTH_LONG).show();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Homeless current_homeless=new Homeless();
                current_homeless.name=name.getText().toString();
                current_homeless.gender=gender.getText().toString();
                current_homeless.age=Integer.parseInt(age.getText().toString());
                current_homeless.other=other.getText().toString();
                current_homeless.tagged_by=firebaseAuth.getCurrentUser().getEmail();
                current_homeless.downvotes=0;
                current_homeless.upvotes=0;
                current_homeless.pic_data_url="nil";
                Location_Data locdata=new Gson().fromJson(z,new TypeToken<Location_Data>(){}.getType());
                current_homeless.loc_data=locdata;

                try {
                    database.child("Homeless").child(System.currentTimeMillis() + "").setValue(current_homeless);
                }
                catch(Exception e)
                {

                }
                Intent intent=new Intent(Fill_homeless_details.this,NavigationCentre.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
