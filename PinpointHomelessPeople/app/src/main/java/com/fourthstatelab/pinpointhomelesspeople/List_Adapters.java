package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.fourthstatelab.pinpointhomelesspeople.Utility.nunito_bold;
import static com.fourthstatelab.pinpointhomelesspeople.Utility.nunito_reg;

/**
 * Created by sparsha on 15/4/17.
 */

public class List_Adapters {
}

class Homeless_list extends BaseAdapter{
    List<Homeless> home_list;
    LayoutInflater inflater;
    Context context;

    Homeless_list(Context con,List<Homeless> list)
    {
        home_list=list;
        context=con;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return home_list.size();
    }

    @Override
    public Object getItem(int i) {
        return home_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class Holder
    {
        TextView name,age,others;
        ImageView homelessimage;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myview = inflater.inflate(R.layout.card_homeless,null);

        Homeless homeless =  home_list.get(i);

        TextView nameView =(TextView)myview.findViewById(R.id.homeless_name);
        nameView.setText(homeless.name);
        nameView.setTypeface(nunito_bold);


        TextView ageView =(TextView)myview.findViewById(R.id.homeless_age);
        ageView.setText("Age: "+homeless.age);
        ageView.setTypeface(nunito_reg);


        TextView genderView = (TextView)myview.findViewById(R.id.homeless_Gender);
        genderView.setText(homeless.gender);
        genderView.setTypeface(nunito_reg);



        TextView descView = (TextView)myview.findViewById(R.id.homeless_desc);
        descView.setText(homeless.other);
        descView.setTypeface(nunito_reg);


        TextView taggedView =(TextView)myview.findViewById(R.id.homeless_taggedBy);
        taggedView.setText("Tagged By: "+homeless.tagged_by);
        taggedView.setTypeface(nunito_reg);



        StorageReference storage= FirebaseStorage.getInstance().getReference();
        StorageReference s1=storage.child("images/"+nameView.getText().toString()+".jpg");
        ImageView homelessimage=(ImageView)myview.findViewById(R.id.homeless_pic);
        if(homeless.pic_data_url==null || homeless.pic_data_url.equals("nil")==false) {

            Glide.with(context).using(new FirebaseImageLoader()).load(s1).into(homelessimage);
        }
        else
        {

        }


        return myview;
    }

    public void notify(List<Homeless> newlist)
    {
        home_list=newlist;
        notifyDataSetChanged();
    }
}



class Food_Distribution_list extends BaseAdapter
{
    List<FoodDistribution> foodlist;
    LayoutInflater layoutInflater;
    Context context;

    Food_Distribution_list(List<FoodDistribution> food_dis,Context con)
    {
        foodlist=food_dis;
        context=con;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return foodlist.size();
    }

    @Override
    public Object getItem(int i) {
        return foodlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class Holder
    {
        TextView name;
        TextView quantity;
        TextView phone;
        TextView address;
        TextView type;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View myview= layoutInflater.inflate(R.layout.food_dist_listview,null);
        Holder holder=new Holder();
        holder.name= (TextView) myview.findViewById(R.id.nameofhomeless);
        holder.address=(TextView)myview.findViewById(R.id.address);
        holder.phone=(TextView)myview.findViewById(R.id.ph_no);
        holder.quantity=(TextView)myview.findViewById(R.id.quantity);
        holder.type=(TextView)myview.findViewById(R.id.mytype);

        holder.name.setText(foodlist.get(i).name_of_provider);
        holder.quantity.setText(foodlist.get(i).quantity+"");
        holder.address.setText(foodlist.get(i).address);
        holder.phone.setText(foodlist.get(i).phone_number);
        holder.type.setText(foodlist.get(i).veg_nonveg+"");



        return myview;
    }

    public void notify_myfunc(List<FoodDistribution> flist)
    {
        foodlist=flist;
        notifyDataSetChanged();
    }
}
