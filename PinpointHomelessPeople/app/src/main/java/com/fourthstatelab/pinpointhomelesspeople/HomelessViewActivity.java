package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        StorageReference storage= FirebaseStorage.getInstance().getReference();
        StorageReference s1=storage.child("images/"+name.getText().toString()+".jpg");
        ImageView imageView=(ImageView)findViewById(R.id.pictureofhomeless);
        if(curr_homeless.pic_data_url==null || curr_homeless.pic_data_url.equals("nil")==false) {

            Glide.with(this).using(new FirebaseImageLoader()).load(s1).into(imageView);
        }
        else
        {

        }


    }
}
