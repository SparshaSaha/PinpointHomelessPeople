package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomelessViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertdialoghomelessdetails);
        TextView name=(TextView)findViewById(R.id.nameofhomeless);
        TextView age=(TextView)findViewById(R.id.ageofhomeless);
        TextView sex=(TextView)findViewById(R.id.sexofhomeless);
        TextView other=(TextView)findViewById(R.id.otherdetailshomeless);
        Intent prev_intent=getIntent();
        int i=prev_intent.getIntExtra("index",0);

        Homeless curr_homeless=Data_holder.Homeless_list.get(i);
        name.setText(curr_homeless.name+"");
        age.setText(curr_homeless.age+"");
        sex.setText(curr_homeless.gender);
        other.setText(curr_homeless.other);

    }
}
